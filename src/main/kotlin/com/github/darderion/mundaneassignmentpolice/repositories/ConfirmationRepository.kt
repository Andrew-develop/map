package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.confirmation.Confirmation
import com.github.darderion.mundaneassignmentpolice.models.entities.ConfirmationEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.ConfirmationsTable
import org.apache.catalina.User
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

interface ConfirmationRepository {
    fun findById(id: UUID): Confirmation?
    fun insert(userId: Long, code: Int, expiresAt: Instant, token: String?): Confirmation?
    fun delete(userId: Long): Unit
}
@Repository
class ConfirmationRepositoryImpl: ConfirmationRepository {
    override fun findById(id: UUID): Confirmation? = transaction {
        ConfirmationEntity.findById(id)?.load(ConfirmationEntity::user)?.let { Confirmation(it) }
    }

    override fun insert(userId: Long, code: Int, expiresAt: Instant, token: String?): Confirmation? = transaction {
        val user = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        ConfirmationEntity.new {
            this.user = user
            this.code = code
            this.expiresAt = expiresAt
            this.token = token
        }.let { Confirmation(it) }
    }

    override fun delete(userId: Long): Unit = transaction {
        ConfirmationEntity.find {
            ConfirmationsTable.userId eq userId
        }.toList().map { it.delete() }
    }
}