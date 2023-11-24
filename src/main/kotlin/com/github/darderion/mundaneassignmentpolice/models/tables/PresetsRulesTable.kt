package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PresetsRulesTable : Table("presets_rules") {
    val presetId = reference("preset_id", PresetsTable, ReferenceOption.CASCADE)
    val ruleId = reference("rule_id", RulesTable, ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(presetId, ruleId)
}