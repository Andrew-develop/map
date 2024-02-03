package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.subscription.SubscriptionResponse
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.services.SubscriptionService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        return ResponseEntity.ok(subscriptionService.getSubscriptionBy(user.subscriptionId))
    }

    @GetMapping("/all")
    fun getAllSubscriptions(): ResponseEntity<*> {
        return ResponseEntity.ok(SubscriptionResponse(subscriptionService.getAllSubscriptions()))
    }

    @PostMapping("/apply/{id}")
    fun applySubscription(@PathVariable id: Int, principal: Principal): ResponseEntity<*> {
        subscriptionService.getSubscriptionBy(id)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Sub don't exist"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(UserDto(userService.applySubscription(id, principal.name)))
    }
}