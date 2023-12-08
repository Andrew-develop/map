package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.review.ReviewDto
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.ReviewEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.ReviewsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.Instant

interface ReviewRepository {
    fun findByIds(ids: List<Long>): List<ReviewDto>
    fun findByUser(id: Long): List<ReviewDto>
    fun findById(id: Long): ReviewDto?
    fun insert(filepath: String, presetId: Long, projectId: Long?, userId: Long): ReviewDto
    fun delete(id: Long): Unit
}

@Repository
class ReviewRepositoryImpl: ReviewRepository {
    override fun findByIds(ids: List<Long>): List<ReviewDto> = transaction {
        ReviewEntity.forIds(ids).toList().map { ReviewDto(it) }
    }
    override fun findByUser(id: Long): List<ReviewDto> = transaction {
        ReviewEntity.find { ReviewsTable.userId eq id }.toList().map { ReviewDto(it) }
    }

    override fun findById(id: Long): ReviewDto? = transaction {
        ReviewEntity.findById(id)?.let { ReviewDto(it) }
    }

    override fun insert(filepath: String, presetId: Long, projectId: Long?, userId: Long): ReviewDto = transaction {
        val userEntity = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")
        val presetEntity = PresetEntity.findById(presetId) ?: throw NoSuchElementException("Error getting preset. Statement result is null.")
        val projectEntity = ProjectEntity.findById(projectId ?: throw NoSuchElementException("Error getting preset. Statement result is null."))

        ReviewEntity.new {
            this.filepath = filepath
            this.revDate = Instant.now()
            this.preset = presetEntity
            this.user = userEntity
            this.project = projectEntity
        }.let { ReviewDto(it) }
    }

    override fun delete(id: Long): Unit = transaction {
        ReviewEntity.findById(id)?.delete()
    }
}