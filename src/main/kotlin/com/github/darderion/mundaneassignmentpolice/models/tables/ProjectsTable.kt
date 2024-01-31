package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object ProjectsTable : LongIdTable("projects") {
    var name = varchar("name", 80)
    val userId = reference("user_id", UsersTable)

    init {
        uniqueIndex(name, userId)
    }
}
