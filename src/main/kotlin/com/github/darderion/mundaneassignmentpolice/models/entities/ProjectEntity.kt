package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.ProjectsTable
import com.github.darderion.mundaneassignmentpolice.models.tables.ReviewsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProjectEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ProjectEntity>(ProjectsTable)

    var name by ProjectsTable.name
    var user by UserEntity referencedOn ProjectsTable.userId
    val reviews by ReviewEntity optionalReferrersOn ReviewsTable.projectId
}
