package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object ReviewsTable : LongIdTable("reviews") {
    val filepath = varchar("filepath", 80)
    val revDate = timestamp("rev_date")

    val userId = reference("user_id", UsersTable)
    val presetId = reference("preset_id", PresetsTable)
    var projectId = reference("project_id", ProjectsTable).nullable()

    init {
        uniqueIndex(filepath)
    }
}
