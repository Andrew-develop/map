package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.project.Project
import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectRequest
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.ProjectsTable
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface ProjectRepository {
    fun findByUser(id: Long): List<Project>
    fun findById(id: Long): Project?
    fun insert(project: ProjectRequest, userId: Long): Project
    fun update(project: ProjectRequest, projectId: Long): Project
    fun delete(id: Long): Unit
}

@Repository
class ProjectRepositoryImpl: ProjectRepository {
    override fun findByUser(id: Long): List<Project> = transaction {
        ProjectEntity.find { ProjectsTable.userId eq id }.map { Project(it) }
    }

    override fun findById(id: Long): Project? = transaction {
        ProjectEntity.findById(id)?.load(ProjectEntity::user)?.let { Project(it) }
    }

    override fun insert(project: ProjectRequest, userId: Long): Project = transaction {
        val userEntity = UserEntity.findById(userId)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        ProjectEntity.new {
            this.name = project.name
            this.user = userEntity
        }.let { Project(it) }
    }

    override fun update(project: ProjectRequest, projectId: Long): Project = transaction {
        val projectEntity = ProjectEntity.findById(projectId)
                ?: throw NoSuchElementException("Error getting project. Statement result is null.")

        projectEntity.name = project.name

        Project(projectEntity)
    }

    override fun delete(id: Long): Unit = transaction {
        ProjectEntity.findById(id)?.delete()
    }
}