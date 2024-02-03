package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.review.Review
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.ReviewEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.ReviewsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.Instant

interface ReviewRepository {
    fun findByProject(id: Long): List<Review>
    fun findByUser(id: Long): List<Review>
    fun findById(id: Long): Review?
    fun insert(filepath: String, presetId: Long, projectId: Long?, userId: Long): Review
    fun delete(id: Long): Unit
}

@Repository
class ReviewRepositoryImpl: ReviewRepository {
    override fun findByProject(id: Long): List<Review> = transaction {
        ReviewEntity.find { ReviewsTable.projectId eq id }.map { Review(it) }
    }
    override fun findByUser(id: Long): List<Review> = transaction {
        ReviewEntity.find { ReviewsTable.userId eq id }.toList().map { Review(it) }
    }

    override fun findById(id: Long): Review? = transaction {
        ReviewEntity.findById(id)?.let { Review(it) }
    }

    override fun insert(filepath: String, presetId: Long, projectId: Long?, userId: Long): Review = transaction {
        val userEntity = UserEntity.findById(userId)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")
        val presetEntity = PresetEntity.findById(presetId)
                ?: throw NoSuchElementException("Error getting preset. Statement result is null.")
        val projectEntity = ProjectEntity.findById(projectId
                ?: throw NoSuchElementException("Error getting preset. Statement result is null."))

        ReviewEntity.new {
            this.filepath = filepath
            this.revDate = Instant.now()
            this.preset = presetEntity
            this.user = userEntity
            this.project = projectEntity
        }.let { Review(it) }
    }

    override fun delete(id: Long): Unit = transaction {
        ReviewEntity.findById(id)?.delete()
    }
}