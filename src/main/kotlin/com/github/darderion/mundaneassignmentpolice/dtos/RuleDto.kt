package com.github.darderion.mundaneassignmentpolice.dtos

import com.github.darderion.mundaneassignmentpolice.entities.Rule

data class RuleDto(
    val id: Int,
    val name: String
) {
    constructor(rule: Rule): this(
        rule.id,
        rule.name
    )
}
