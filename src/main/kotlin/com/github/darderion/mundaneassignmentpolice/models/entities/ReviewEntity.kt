package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.ReviewsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ReviewEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<ReviewEntity>(ReviewsTable)

    var filepath by ReviewsTable.filepath
    var revDate by ReviewsTable.revDate
    var user by UserEntity referencedOn ReviewsTable.userId
    var preset by PresetEntity referencedOn ReviewsTable.presetId
    var project by ProjectEntity optionalReferencedOn ReviewsTable.projectId
}
