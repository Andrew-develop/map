package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.UUIDDto
import com.github.darderion.mundaneassignmentpolice.dtos.confirmation.ConfirmationRequest
import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtRequest
import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtResponse
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.services.ConfirmationService
import com.github.darderion.mundaneassignmentpolice.services.EmailService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import com.github.darderion.mundaneassignmentpolice.utils.JwtTokenUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController (
        private val userService: UserService,
        private val emailService: EmailService,
        private val confirmationService: ConfirmationService,
        private val jwtTokenUtils: JwtTokenUtils,
        private val authenticationManager: AuthenticationManager
) {
    @PostMapping("/login")
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

    @PostMapping("/register")
    fun createNewUser(@RequestBody request: UserRequest): ResponseEntity<*> {
        val user = userService.createNewUser(request)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User already exist"), HttpStatus.BAD_REQUEST)
        val confirm = confirmationService.prepareConfirmation(user.id)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
//        emailService.sendCode(confirm, user.email, "email confirmation")
        return ResponseEntity.ok(UUIDDto(confirm.id))
    }

    @PostMapping("/registration/confirm")
    fun confirmRegistration(@RequestBody request: ConfirmationRequest): ResponseEntity<*> {
        val userId = confirmationService.confirm(request)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect code"), HttpStatus.BAD_REQUEST)
        val user = userService.confirm(userId)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(user)
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestParam email: String): ResponseEntity<*> {
        val userId = userService.getIdForResetPassword(email)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Email doesn't exist"), HttpStatus.BAD_REQUEST)
        val confirm = confirmationService.prepareConfirmation(userId)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
//        emailService.sendCode(confirm, email, "reset password")
        return ResponseEntity.ok(UUIDDto(confirm.id))
    }

    @PostMapping("/reset-password/confirm")
    fun confirmReset(@RequestBody request: ConfirmationRequest): ResponseEntity<*> {
        val userId = confirmationService.confirm(request)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect code"), HttpStatus.BAD_REQUEST)
        val password = request.password
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect password"), HttpStatus.BAD_REQUEST)
        val user = userService.resetPassword(userId, password)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(user)
    }
}