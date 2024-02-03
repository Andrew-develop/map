package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.repositories.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserService (
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
): UserDetailsService {

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        )
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        )
        return org.springframework.security.core.userdetails.User (
                username,
                user.password,
                user.roles.toList().stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())
        )
    }

    fun findForReset(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun createNewUser(request: UserRequest): User? {
        userRepository.findByEmail(request.email)?.let { return null }
        return userRepository.insert(
                UserRequest(
                        request.name,
                        passwordEncoder.encode(request.password),
                        request.email
                )
        )
    }

    fun updateUser(request: UserRequest, email: String): User {
        val user = findByEmail(email)
        val processedRequest = UserRequest(
            request.name,
            passwordEncoder.encode(request.password),
            request.email
        )
        return userRepository.update(processedRequest, user.id)
    }

    fun resetPassword(userId: Long, password: String): User {
        return userRepository.resetPassword(userId, passwordEncoder.encode(password))
    }

    fun confirm(userId: Long): User {
        return userRepository.confirm(userId)
    }

    fun applySubscription(id: Int, email: String): User {
        val user = findByEmail(email)
        return userRepository.applySubscription(id, user.id)
    }
}