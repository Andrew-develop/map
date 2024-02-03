package com.github.darderion.mundaneassignmentpolice.dtos.review

import com.github.darderion.mundaneassignmentpolice.models.entities.ReviewEntity
import java.time.Instant

data class Review(
        val id: Long,
        val filepath: String,
        var revDate: Instant,
        var userId: Long,
        var presetId: Long,
        var projectId: Long?
) {
    constructor(review: ReviewEntity): this(
            review.id.value,
            review.filepath,
            review.revDate,
            review.user.id.value,
            review.preset.id.value,
            review.project?.id?.value
    )
}
