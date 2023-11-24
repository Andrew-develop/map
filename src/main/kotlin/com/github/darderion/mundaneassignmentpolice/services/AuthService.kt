package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtRequest
import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtResponse
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.utils.JwtTokenUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody

@Service
class AuthService (
    private val userService: UserService,
    private val jwtTokenUtils: JwtTokenUtils,
    private val authenticationManager: AuthenticationManager
) {
    fun createAuthToken(@RequestBody authRequest: JwtRequest): ResponseEntity<*> {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password))
        } catch (e: BadCredentialsException) {
            return ResponseEntity(AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный email или пароль"), HttpStatus.UNAUTHORIZED)
        }
        val userDetails = userService.loadUserByUsername(authRequest.email)
        val token = jwtTokenUtils.generateToken(userDetails)
        return ResponseEntity.ok(JwtResponse(token))
    }

    fun createNewUser(@RequestBody request: UserRequest): ResponseEntity<*> {
        val user = userService.createNewUser(request)
        return createAuthToken(JwtRequest(user.email, user.password))
    }
}