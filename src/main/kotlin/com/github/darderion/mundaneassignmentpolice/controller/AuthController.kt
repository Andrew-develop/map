package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtRequest
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.services.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (
        private val authService: AuthService
) {
    @PostMapping("/login")
    fun createAuthToken(@RequestBody authRequest: JwtRequest): ResponseEntity<*> {
        return authService.createAuthToken(authRequest)
    }

    @PostMapping("/register")
    fun createNewUser(@RequestBody request: UserRequest): ResponseEntity<*> {
        return authService.createNewUser(request)
    }
}