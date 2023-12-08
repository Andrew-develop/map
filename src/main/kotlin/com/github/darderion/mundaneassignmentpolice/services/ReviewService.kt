package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.checker.Checker
import com.github.darderion.mundaneassignmentpolice.checker.DocumentReport
import com.github.darderion.mundaneassignmentpolice.controller.pdfFolder
import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewDto
import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewResponse
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.repositories.ReviewRepository
import com.github.darderion.mundaneassignmentpolice.rules.RuleSet
import com.github.darderion.mundaneassignmentpolice.utils.FileUploadUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ReviewService (
        private val reviewRepository: ReviewRepository
) {
    fun getAllUserReviews(user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(ReviewResponse(reviewRepository.findByUser(user.id)))
    }

    fun getSingleUserReviews(user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(ReviewResponse(reviewRepository.findByUser(user.id).filter { it.projectId == null }))
    }

    fun getReviewBy(id: Long): ReviewDto {
        return reviewRepository.findById(id) ?: throw NoSuchElementException(
                "Error getting review. Statement result is null."
        )
    }

    fun getViolations(pdfName: String, rules: RuleSet): ResponseEntity<*> {
        val ruleViolations = Checker().getRuleViolations("$pdfFolder${pdfName}", rules)
        return ResponseEntity.ok(DocumentReport(pdfName, ruleViolations, 0))
    }

    fun createReview(file: MultipartFile, user: UserDto, rules: RuleSet, presetId: Long, projectId: Long?): ResponseEntity<*> {
        if (file.originalFilename == null) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "Empty file"), HttpStatus.BAD_REQUEST)
        }
        val pdfName = FileUploadUtil.saveFile(pdfFolder, file, folderMaxSize = 1000)

        return ResponseEntity.ok(reviewRepository.insert(pdfName, presetId, projectId, user.id))
    }

    fun deleteReviewBy(id: Long, user: UserDto): ResponseEntity<*> {
        if (reviewRepository.findById(id)?.userId != user.id) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have review"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(reviewRepository.delete(id))
    }
}