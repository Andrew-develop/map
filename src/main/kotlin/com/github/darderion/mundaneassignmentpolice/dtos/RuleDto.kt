package com.github.darderion.mundaneassignmentpolice.dtos

import com.github.darderion.mundaneassignmentpolice.models.entities.RuleEntity

data class RuleDto(
    val id: Int,
    val name: String
) {
    constructor(rule: RuleEntity): this(
        rule.id.value,
        rule.name
    )
}
