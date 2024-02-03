package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewDto
import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewResponse
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity
import com.github.darderion.mundaneassignmentpolice.services.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.security.Principal

@RestController
@RequestMapping("/review")
class ReviewController (
        private val reviewService: ReviewService,
        private val presetService: PresetService,
        private val projectService: ProjectService,
        private val ruleService: RuleService,
        private val userService: UserService
) {
    @GetMapping("/all")
    fun getUserReviews(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(ReviewResponse(reviewService.getSingleUserReviews(user).map { ReviewDto(it) }))
    }

    @PostMapping("/create")
    fun createReview(@RequestParam file: MultipartFile,
                     @RequestParam presetId: Long,
                     @RequestParam projectId: Long?,
                     principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val preset = presetService.getPresetBy(presetId)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Preset not found"), HttpStatus.BAD_REQUEST)
        val project = if (projectId != null) projectService.getProjectBy(projectId) else null
        val review = reviewService.createReview(file, user, preset, project)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Empty file"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(ReviewDto(review))
    }

    @GetMapping("/{id}/violations")
    fun getViolations(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val review = reviewService.getReviewBy(id, user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have review"), HttpStatus.BAD_REQUEST)
        val preset = presetService.getPresetBy(review.presetId)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have review"), HttpStatus.BAD_REQUEST)
        val rules = ruleService.convertToSet(preset.rules)
        return ResponseEntity.ok(reviewService.getViolations(review.filepath, rules))
    }

    @DeleteMapping("/delete/{id}")
    fun deleteReview(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        reviewService.deleteReviewBy(id, user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User isn't owner of review"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}