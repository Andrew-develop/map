package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.services.ProjectService
import com.github.darderion.mundaneassignmentpolice.services.ReviewService
import com.github.darderion.mundaneassignmentpolice.services.RuleService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/review")
class ReviewController (
        private val reviewService: ReviewService,
        private val ruleService: RuleService,
        private val userService: UserService
) {
    @GetMapping("/all")
    fun getUserReviews(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return reviewService.getSingleUserReviews(user)
    }

    @PostMapping("/create")
    fun createReview(@RequestParam file: MultipartFile,
                     @RequestParam presetId: Long,
                     @RequestParam projectId: Long?,
                     principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val rules = ruleService.findByPreset(presetId)
        return reviewService.createReview(file, user, rules, presetId, projectId)
    }

    @GetMapping("/{id}/violations")
    fun getViolations(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val review = reviewService.getReviewBy(id)
        val rules = ruleService.findByPreset(review.presetId)
        return reviewService.getViolations(review.filepath, rules)
    }

    @DeleteMapping("/delete/{id}")
    fun deleteReview(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return reviewService.deleteReviewBy(id, user)
    }
}