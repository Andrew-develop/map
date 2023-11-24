package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object RolesTable : IntIdTable("roles") {
    var name = varchar("name", 80)

    init {
        uniqueIndex(name)
    }
}