package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.RulesTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RuleEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<RuleEntity>(RulesTable)

    var name by RulesTable.name
}
