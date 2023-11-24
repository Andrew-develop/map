package com.github.darderion.mundaneassignmentpolice.dtos.project

import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.models.entities.ProjectEntity

data class ProjectDto(
        val id: Long,
        val name: String,
        val userId: Long
) {
        constructor(project: ProjectEntity): this(
                project.id.value,
                project.name,
                project.user.id.value
        )
}
