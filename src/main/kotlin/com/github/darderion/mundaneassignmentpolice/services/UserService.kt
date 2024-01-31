package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.user.SecurityUser
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.repositories.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    fun findByEmail(email: String): UserDto {
        return userRepository.findByEmail(email) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        )
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findSecurityByEmail(username) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        )
        return org.springframework.security.core.userdetails.User (
                username,
                user.password,
                user.roles.toList().stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())
        )
    }

    fun createNewUser(request: UserRequest): UserDto? {
        userRepository.findByEmail(request.email)?.let { return null }
        val user = userRepository.insert(
                UserRequest(
                        request.name,
                        passwordEncoder.encode(request.password),
                        request.email
                )
        )
        return user
    }

    fun updateUser(request: UserRequest, email: String): ResponseEntity<*> {
        val user = findByEmail(email)
        val processedRequest = UserRequest(
            request.name,
            passwordEncoder.encode(request.password),
            request.email
        )
        return ResponseEntity.ok(userRepository.update(processedRequest, user.id))
    }

    fun getIdForResetPassword(email: String): Long? {
        return userRepository.findByEmail(email)?.let { return it.id }
    }

    fun resetPassword(userId: Long, password: String): UserDto? {
        return userRepository.resetPassword(userId, passwordEncoder.encode(password))
    }

    fun confirm(userId: Long): UserDto? {
        return userRepository.confirm(userId)
    }

    fun applySubscription(subId: Int, email: String): UserDto {
        val user = findByEmail(email)
        return userRepository.updateSubscription(subId, user.id)
    }
}