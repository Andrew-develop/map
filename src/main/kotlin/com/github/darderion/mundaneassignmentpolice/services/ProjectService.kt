package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectDto
import com.github.darderion.mundaneassignmentpolice.entities.Project
import com.github.darderion.mundaneassignmentpolice.entities.User
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.exceptions.NotFoundException
import com.github.darderion.mundaneassignmentpolice.repositories.ProjectRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProjectService (
        private val projectRepository: ProjectRepository
) {
    fun getUserProjects(user: User): ResponseEntity<*> {
        return ResponseEntity.ok(projectRepository.findByUser(user).map {
            ProjectDto(it)
        })
    }

    fun createNewProject(name: String, user: User) {
        val project = Project(
                name = name,
                user = user
        )
        projectRepository.save(project)
    }

    @Transactional
    fun updateProject(id: Long, name: String, user: User): ResponseEntity<*> {
        val project = projectRepository.findById(id).orElseThrow {
            NotFoundException(String.format("Проекта '%s' не существует", id))
        }
        if (project.user == user) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "У пользователя нет проекта" + id), HttpStatus.BAD_REQUEST)
        }
        project.name = name
        projectRepository.save(project)
        return ResponseEntity.ok("preset successfully updated")
    }

    @Transactional
    fun deleteProjectBy(id: Long, user: User): ResponseEntity<*> {
        val project = projectRepository.findById(id).orElseThrow {
            NotFoundException(String.format("Проекта '%s' не существует", id))
        }
        if (project.user == user) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "У пользователя нет проекта" + id), HttpStatus.BAD_REQUEST)
        }
        projectRepository.delete(project)
        return ResponseEntity.ok("preset successfully deleted")
    }
}