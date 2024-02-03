package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.checker.Checker
import com.github.darderion.mundaneassignmentpolice.checker.DocumentReport
import com.github.darderion.mundaneassignmentpolice.controller.pdfFolder
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.dtos.project.Project
import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectDto
import com.github.darderion.mundaneassignmentpolice.dtos.review.Review
import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.ReviewEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.repositories.PresetRepository
import com.github.darderion.mundaneassignmentpolice.repositories.ProjectRepository
import com.github.darderion.mundaneassignmentpolice.repositories.ReviewRepository
import com.github.darderion.mundaneassignmentpolice.rules.RuleSet
import com.github.darderion.mundaneassignmentpolice.utils.FileUploadUtil
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ReviewService (
        private val reviewRepository: ReviewRepository,
        private val presetRepository: PresetRepository
) {
    fun getAllUserReviews(user: User): List<Review> {
        return reviewRepository.findByUser(user.id)
    }

    fun getSingleUserReviews(user: User): List<Review> {
        return reviewRepository.findByUser(user.id).filter { it.projectId == null }
    }

    fun getReviewBy(id: Long, user: User): Review? {
        val review = reviewRepository.findById(id) ?: return null
        return if (review.userId == user.id) review else null
    }

    fun getViolations(pdfName: String, rules: RuleSet): DocumentReport {
        val ruleViolations = Checker().getRuleViolations("$pdfFolder${pdfName}", rules)
        return DocumentReport(pdfName, ruleViolations, 0)
    }

    fun createReview(file: MultipartFile, user: User, preset: PresetDto, project: Project?): Review? {
        file.originalFilename ?: return null
        if (presetRepository.findByUser(user.id).contains(preset)) {
            return null
        }
        if (project != null && project.id != user.id) {
            return null
        }
        val pdfName = FileUploadUtil.saveFile(pdfFolder, file, folderMaxSize = 1000)
        return reviewRepository.insert(pdfName, preset.id, project?.id, user.id)
    }

    fun deleteReviewBy(id: Long, user: User): Unit? {
        val review = reviewRepository.findById(id) ?: return null
        return if (review.userId == user.id)
            reviewRepository.delete(id) else null
    }
}