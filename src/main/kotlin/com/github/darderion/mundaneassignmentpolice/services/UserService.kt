package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.UserDto
import com.github.darderion.mundaneassignmentpolice.entities.RulePack
import com.github.darderion.mundaneassignmentpolice.entities.Subscription
import com.github.darderion.mundaneassignmentpolice.repositories.UserRepository
import com.github.darderion.mundaneassignmentpolice.entities.User
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class UserService (
        private val userRepository: UserRepository,
        private val roleService: RoleService,
        private val passwordEncoder: BCryptPasswordEncoder
): UserDetailsService {
    fun findForRegistration(email: String): User? {
        return userRepository.findByEmail(email)
    }
    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", email)
        )
    }

    @Transactional
    override fun loadUserByUsername(username: String): UserDetails {
        val user: User = findByEmail(username) ?: throw UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        )
        return org.springframework.security.core.userdetails.User (
                username,
                user.password,
                user.roles.stream().map { SimpleGrantedAuthority(it.name) }.collect(Collectors.toList())
        )
    }

    fun createNewUser(request: UserDto, sub: Subscription, rulePacks: List<RulePack>): User {
        val user = User(
            firstname = request.firstname,
            lastname = request.lastname,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            subscription = sub,
            rulePacks = rulePacks,
            roles = listOf(roleService.getUserRole())
        )
        return userRepository.save(user)
    }

    @Transactional
    fun applySubscription(subscription: Subscription, email: String): ResponseEntity<*> {
        val user = findByEmail(email)
        user.subscription = subscription
        userRepository.save(user)
        return ResponseEntity.ok("subscription" + subscription.name + "has been issued for" + email)
    }

    @Transactional
    fun addRulePack(rulePack: RulePack, email: String): ResponseEntity<*> {
        val user = findByEmail(email)
        if (user.rulePacks.contains(rulePack)) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Набор был добавлен ранее"), HttpStatus.BAD_REQUEST)
        }
        user.rulePacks += rulePack
        userRepository.save(user)
        return ResponseEntity.ok("rule pack" + rulePack.name + "has been added for" + email)
    }
}