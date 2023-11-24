package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table

object PresetsTable : LongIdTable("presets") {
    var name = varchar("name", 80)

    init {
        uniqueIndex(name)
    }
}
