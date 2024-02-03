package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object PresetsTable : LongIdTable("presets") {
    var name = varchar("name", 80)
    var ownerId = reference("owner_id", UsersTable).nullable()

    init {
        uniqueIndex(name)
    }
}
