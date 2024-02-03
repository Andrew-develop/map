package com.github.darderion.mundaneassignmentpolice.dtos.confirmation

import com.github.darderion.mundaneassignmentpolice.models.entities.ConfirmationEntity
import java.time.Instant
import java.util.UUID

data class Confirmation(
        val id: UUID,
        val code: Int,
        val expiresAt: Instant,
        val token: String?,
        val userId: Long
) {
    constructor(confirmation: ConfirmationEntity): this(
        confirmation.id.value,
        confirmation.code,
        confirmation.expiresAt,
        confirmation.token,
        confirmation.user.id.value,
    )
}
