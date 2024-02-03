package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.confirmation.Confirmation
import com.github.darderion.mundaneassignmentpolice.dtos.confirmation.ConfirmationRequest
import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.repositories.ConfirmationRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ConfirmationService (
    private val confirmationRepository: ConfirmationRepository
) {
    fun prepareConfirmation(user: User, token: String? = null): Confirmation? {
        val code = (1000..9999).random()
        val expiresAt = Instant.now().plusSeconds(300)
        return confirmationRepository.insert(user.id, code, expiresAt, token)
    }

    fun getConfirmation(request: ConfirmationRequest): Confirmation? {
        val confirmation = confirmationRepository.findById(request.id) ?: return null

        val codesEquals = confirmation.code == request.code
        val expires = confirmation.expiresAt.isAfter(Instant.now())

        confirmationRepository.delete(confirmation.userId)

        return if (codesEquals && expires) confirmation else null
    }
}