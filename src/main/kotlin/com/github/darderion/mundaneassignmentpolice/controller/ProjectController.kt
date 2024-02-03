package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectDto
import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectRequest
import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectResponse
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.services.ProjectService
import com.github.darderion.mundaneassignmentpolice.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/project")
class ProjectController (
        private val userService: UserService,
        private val projectService: ProjectService
) {
    @GetMapping("/all")
    fun getUserProjects(principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(ProjectResponse(projectService.getUserProjects(user).map { ProjectDto(it) }))
    }

    @PostMapping("/create")
    fun createPreset(@RequestBody request: ProjectRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        return ResponseEntity.ok(ProjectDto(projectService.createProject(request, user)))
    }

    @PutMapping("/update/{id}")
    fun updatePreset(@PathVariable id: Long, @RequestBody request: ProjectRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        val project = projectService.updateProject(request, id, user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have project"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(ProjectDto(project))
    }

    @DeleteMapping("/delete/{id}")
    fun deletePreset(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        projectService.deleteProjectBy(id, user)
                ?: return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User isn't owner of project"), HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(HttpStatus.OK)
    }
}