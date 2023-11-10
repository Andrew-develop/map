package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.UserDto
import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtRequest
import com.github.darderion.mundaneassignmentpolice.services.AuthService
import com.github.darderion.mundaneassignmentpolice.services.RulePackService
import com.github.darderion.mundaneassignmentpolice.services.SubscriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController (
    private val authService: AuthService,
    private val subscriptionService: SubscriptionService,
    private val rulePackService: RulePackService
) {
    @PostMapping("/login")
    fun createAuthToken(@RequestBody authRequest: JwtRequest): ResponseEntity<*> {
        return authService.createAuthToken(authRequest)
    }

    @PostMapping("/register")
    fun createNewUser(@RequestBody request: UserDto): ResponseEntity<*> {
        val baseSub = subscriptionService.getBaseSubscription()
        val baseRulePacks = rulePackService.getBaseRulePacks()
        return authService.createNewUser(request, baseSub, baseRulePacks)
    }
}