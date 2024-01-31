package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.confirmation.ConfirmationDto
import com.github.darderion.mundaneassignmentpolice.models.entities.ConfirmationEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.ConfirmationsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

interface ConfirmationRepository {
    fun findById(id: UUID): ConfirmationDto?
    fun insert(userId: Long, code: Int, expiresAt: Instant): ConfirmationDto?
    fun delete(userId: Long): Unit
}
@Repository
class ConfirmationRepositoryImpl: ConfirmationRepository {
    override fun findById(id: UUID): ConfirmationDto? = transaction {
        ConfirmationEntity.findById(id)?.let { ConfirmationDto(it) }
    }

    override fun insert(userId: Long, code: Int, expiresAt: Instant): ConfirmationDto? = transaction {
        val user = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        ConfirmationEntity.new {
            this.user = user
            this.code = code
            this.expiresAt = expiresAt
        }.let { ConfirmationDto(it) }
    }

    override fun delete(userId: Long): Unit = transaction {
        ConfirmationEntity.find {
            ConfirmationsTable.userId eq userId
        }.toList().map { it.delete() }
    }
}