package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object RulesTable : IntIdTable("rules") {
    var name = varchar("name", 80)

    init {
        uniqueIndex(name)
    }
}