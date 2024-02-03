package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.project.Project
import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectRequest
import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.repositories.ProjectRepository
import org.springframework.stereotype.Service

@Service
class ProjectService (
        private val projectRepository: ProjectRepository
) {
    fun getProjectBy(id: Long): Project? {
        return projectRepository.findById(id)
    }

    fun getUserProjects(user: User): List<Project> {
        return projectRepository.findByUser(user.id)
    }

    fun createProject(projectRequest: ProjectRequest, user: User): Project {
        return projectRepository.insert(projectRequest, user.id)
    }

    fun updateProject(request: ProjectRequest, id: Long, user: User): Project? {
        val project = projectRepository.findById(id) ?: return null
        return if (project.userId == user.id)
            projectRepository.update(request, id) else null
    }

    fun deleteProjectBy(id: Long, user: User): Unit? {
        val project = projectRepository.findById(id) ?: return null
        return if (project.userId == user.id)
            projectRepository.delete(id) else null
    }
}