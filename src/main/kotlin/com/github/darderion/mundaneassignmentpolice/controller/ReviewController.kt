package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewRequest
import com.github.darderion.mundaneassignmentpolice.services.ReviewService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/review")
class ReviewController (
        private val reviewService: ReviewService,
        private val userService: UserService
) {
    @GetMapping("/all")
    fun getUserPresets(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return reviewService.getSingleUserReviews(user)
    }

    @PostMapping("/create")
    fun createPreset(@RequestBody request: ReviewRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return reviewService.createReview(request, user)
    }

    @DeleteMapping("/delete/{id}")
    fun deletePreset(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return reviewService.deleteReviewBy(id, user)
    }
}