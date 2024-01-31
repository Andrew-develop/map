package com.github.darderion.mundaneassignmentpolice.dtos.confirmation

import com.github.darderion.mundaneassignmentpolice.models.entities.ConfirmationEntity
import java.time.Instant
import java.util.UUID

data class ConfirmationDto(
        val id: UUID,
        val userId: Long,
        val code: Int,
        val expiresAt: Instant
) {
    constructor(confirmation: ConfirmationEntity): this(
            confirmation.id.value,
            confirmation.user.id.value,
            confirmation.code,
            confirmation.expiresAt
    )
}
