package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.*
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class PresetEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<PresetEntity>(PresetsTable)

    var name by PresetsTable.name
    var rules by RuleEntity via PresetsRulesTable
    var users by UserEntity via UsersPresetsTable
}
