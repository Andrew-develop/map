package com.github.darderion.mundaneassignmentpolice.controller

import com.github.darderion.mundaneassignmentpolice.dtos.project.ProjectRequest
import com.github.darderion.mundaneassignmentpolice.services.ProjectService
import com.github.darderion.mundaneassignmentpolice.services.UserService
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
        return projectService.getUserProjects(user)
    }

    @PostMapping("/create")
    fun createPreset(@RequestBody request: ProjectRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        projectService.createNewProject(request.name, user)
        return ResponseEntity.ok("Project has been added")
    }

    @PutMapping("/update/{id}")
    fun updatePreset(@PathVariable id: Long, @RequestBody request: ProjectRequest, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        projectService.updateProject(id, request.name, user)
        return ResponseEntity.ok("Project has been updated")
    }

    @DeleteMapping("/delete/{id}")
    fun deletePreset(@PathVariable id: Long, principal: Principal): ResponseEntity<*> {
        val user = userService.findByEmail(principal.name)
        projectService.deleteProjectBy(id, user)
        return ResponseEntity.ok("Project has been deleted")
    }
}