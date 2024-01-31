package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp

object ConfirmationsTable : UUIDTable("confirmations") {
    var code = integer("code")
    var expiresAt = timestamp("expires_at")
    var userId = reference("user_id", UsersTable)
}