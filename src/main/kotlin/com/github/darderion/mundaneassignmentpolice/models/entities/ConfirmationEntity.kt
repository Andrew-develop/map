package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.ConfirmationsTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class ConfirmationEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<ConfirmationEntity>(ConfirmationsTable)

    var code by ConfirmationsTable.code
    var expiresAt by ConfirmationsTable.expiresAt
    var token by ConfirmationsTable.token
    var user by UserEntity referencedOn ConfirmationsTable.userId
}