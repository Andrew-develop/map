package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewRequest
import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewResponse
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.repositories.ReviewRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

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

    fun createReview(review: ReviewRequest, user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(reviewRepository.insert(review, user.id))
    }

    fun deleteReviewBy(id: Long, user: UserDto): ResponseEntity<*> {
        if (reviewRepository.findById(id)?.userId != user.id) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have review"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(reviewRepository.delete(id))
    }
}