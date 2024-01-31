package com.github.darderion.mundaneassignmentpolice.dtos.confirmation

import java.util.UUID

data class ConfirmationRequest(
        val id: UUID,
        val code: Int,
        val password: String? = null
)