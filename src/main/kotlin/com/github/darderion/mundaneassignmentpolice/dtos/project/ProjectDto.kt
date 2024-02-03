package com.github.darderion.mundaneassignmentpolice.dtos.project

data class ProjectDto(
        val id: Long,
        val name: String
) {
        constructor(project: Project): this(
                project.id,
                project.name
        )
}
