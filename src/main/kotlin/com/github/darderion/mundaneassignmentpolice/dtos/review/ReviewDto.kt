package com.github.darderion.mundaneassignmentpolice.dtos.review

import com.github.darderion.mundaneassignmentpolice.checker.RuleViolation
import com.github.darderion.mundaneassignmentpolice.models.entities.ReviewEntity
import java.sql.Timestamp

data class ReviewDto(
        var id: Long,
        val filepath: String,
        val revDate: Timestamp,
        val presetId: Long,
        val userId: Long,
        val projectId: Long?
) {
    constructor(review: ReviewEntity): this(
            review.id.value,
            review.filepath,
            Timestamp.from(review.revDate),
            review.preset.id.value,
            review.user.id.value,
            review.project?.id?.value
    )
}