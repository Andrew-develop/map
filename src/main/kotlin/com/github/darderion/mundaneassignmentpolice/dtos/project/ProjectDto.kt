package com.github.darderion.mundaneassignmentpolice.dtos.project

import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity

data class ProjectDto(
        val id: Long,
        val name: String
) {
        constructor(project: Project): this(
                project.id,
                project.name
        )
}