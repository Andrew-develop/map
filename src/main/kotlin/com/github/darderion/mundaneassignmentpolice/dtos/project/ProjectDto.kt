package com.github.darderion.mundaneassignmentpolice.dtos.project

import com.github.darderion.mundaneassignmentpolice.entities.Project

data class ProjectDto(
        val id: Long?,
        val name: String
) {
    constructor(project: Project): this(
            project.id,
            project.name
    )
}
