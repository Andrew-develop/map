package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.SubscriptionDto
import com.github.darderion.mundaneassignmentpolice.services.SubscriptionService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/subscription")
class SubscriptionController (
        private val subscriptionService: SubscriptionService,
        private val userService: UserService
) {
    @GetMapping("/current")
    fun getCurrentSubscription(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(SubscriptionDto(user.subscription))
    }

    @GetMapping("/all")
    fun getAllSubscriptions(): ResponseEntity<*> {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions().map {
            SubscriptionDto(it)
        })
    }

    @PostMapping("/apply/{id}")
    fun applySubscription(@PathVariable id: Int, principal: Principal): ResponseEntity<*> {
        val sub = subscriptionService.getSubscriptionBy(id)
        return userService.applySubscription(sub, principal.name)
    }
}