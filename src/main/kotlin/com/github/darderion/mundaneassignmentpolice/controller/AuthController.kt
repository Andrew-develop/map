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
        private val authenticationManager: AuthenticationManager,
        private val jwtTokenUtils: JwtTokenUtils,
        private val userService: UserService,
        private val emailService: EmailService,
        private val confirmationService: ConfirmationService
) {
    @PostMapping("/login")
    fun createAuthToken(@RequestBody authRequest: JwtRequest): ResponseEntity<*> {
        val token = authenticateUser(authRequest)
            ?: return ResponseEntity(AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong email or password"), HttpStatus.UNAUTHORIZED)
        return ResponseEntity.ok(JwtResponse(token))
    }

    @PostMapping("/register")
    fun createNewUser(@RequestBody request: UserRequest): ResponseEntity<*> {
        val user = userService.createNewUser(request)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User already exist"), HttpStatus.BAD_REQUEST)
        val token = authenticateUser(JwtRequest(request.email, request.password))
        val confirm = confirmationService.prepareConfirmation(user, token)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
        emailService.sendCode(confirm.code, user.email, "email confirmation")
        return ResponseEntity.ok(UUIDDto(confirm.id))
    }

    @PostMapping("/registration/confirm")
    fun confirmRegistration(@RequestBody request: ConfirmationRequest): ResponseEntity<*> {
        val confirmation = confirmationService.getConfirmation(request)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect code"), HttpStatus.BAD_REQUEST)
        userService.confirm(confirmation.userId)

        return if (confirmation.token != null)
            ResponseEntity.ok(JwtResponse(confirmation.token))
        else ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/reset-password")
    fun resetPassword(@RequestParam email: String): ResponseEntity<*> {
        val user = userService.findForReset(email)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Email doesn't exist"), HttpStatus.BAD_REQUEST)
        val confirm = confirmationService.prepareConfirmation(user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Something went wrong"), HttpStatus.BAD_REQUEST)
        emailService.sendCode(confirm.code, email, "reset password")
        return ResponseEntity.ok(UUIDDto(confirm.id))
    }

    @PostMapping("/reset-password/confirm")
    fun confirmReset(@RequestBody request: ConfirmationRequest): ResponseEntity<*> {
        val confirmation = confirmationService.getConfirmation(request)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect code"), HttpStatus.BAD_REQUEST)
        val password = request.password
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Incorrect password"), HttpStatus.BAD_REQUEST)
        val user = userService.resetPassword(confirmation.userId, password)
        return createAuthToken(JwtRequest(user.email, password))
    }

    private fun authenticateUser(request: JwtRequest): String? {
        try {
            authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(request.email, request.password)
            )
        } catch (e: BadCredentialsException) {
            return null
        }
        val userDetails = userService.loadUserByUsername(request.email)
        return jwtTokenUtils.generateToken(userDetails)
    }
}