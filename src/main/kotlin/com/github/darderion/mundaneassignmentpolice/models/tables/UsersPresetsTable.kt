package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsersPresetsTable : Table("users_presets") {
    val userId = reference("user_id", UsersTable, ReferenceOption.CASCADE)
    val presetId = reference("preset_id", PresetsTable, ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(userId, presetId)
}