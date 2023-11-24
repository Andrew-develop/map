package com.github.darderion.mundaneassignmentpolice.dtos.review

import com.github.darderion.mundaneassignmentpolice.models.entities.ReviewEntity

data class ReviewResponse(
        val reviews: List<ReviewDto>
)
