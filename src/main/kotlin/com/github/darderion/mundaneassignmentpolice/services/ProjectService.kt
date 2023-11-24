package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectRequest
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.repositories.ProjectRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ProjectService (
        private val projectRepository: ProjectRepository
) {
    fun getUserProjects(user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(projectRepository.findByUser(user.id))
    }

    fun createProject(projectRequest: ProjectRequest, user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(projectRepository.insert(projectRequest, user.id))
    }

    fun updateProject(projectRequest: ProjectRequest, id: Long, user: UserDto): ResponseEntity<*> {
        if (projectRepository.findById(id)?.userId != user.id) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have project"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(projectRepository.update(projectRequest, id))
    }

    fun deleteProjectBy(id: Long, user: UserDto): ResponseEntity<*> {
        if (projectRepository.findById(id)?.userId != user.id) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have project"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(projectRepository.delete(id))
    }
}