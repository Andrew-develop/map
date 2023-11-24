package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectDto
import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectRequest
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.ProjectsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface ProjectRepository {
    fun findByUser(id: Long): List<ProjectDto>
    fun findById(id: Long): ProjectDto?
    fun insert(project: ProjectRequest, userId: Long): ProjectDto
    fun update(project: ProjectRequest, projectId: Long): ProjectDto
    fun delete(id: Long): Unit
}

@Repository
class ProjectRepositoryImpl: ProjectRepository {
    override fun findByUser(id: Long): List<ProjectDto> = transaction {
        ProjectEntity.find { ProjectsTable.userId eq id }.map { ProjectDto(it) }
    }

    override fun findById(id: Long): ProjectDto? = transaction {
        ProjectEntity.findById(id)?.let { ProjectDto(it) }
    }

    override fun insert(project: ProjectRequest, userId: Long): ProjectDto = transaction {
        val userEntity = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        ProjectEntity.new {
            this.name = project.name
            this.user = userEntity
        }.let { ProjectDto(it) }
    }

    override fun update(project: ProjectRequest, projectId: Long): ProjectDto = transaction {
        val projectEntity = ProjectEntity.findById(projectId) ?: throw NoSuchElementException("Error getting project. Statement result is null.")

        projectEntity.name = project.name

        ProjectDto(projectEntity)
    }

    override fun delete(id: Long): Unit = transaction {
        ProjectEntity.findById(id)?.delete()
    }
}