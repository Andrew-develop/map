package com.github.darderion.mundaneassignmentpolice.dtos.review

import java.time.Instant

data class ReviewDto(
        var id: Long,
        val revDate: Instant
) {
    constructor(review: Review): this(
            review.id,
            review.revDate
    )
}