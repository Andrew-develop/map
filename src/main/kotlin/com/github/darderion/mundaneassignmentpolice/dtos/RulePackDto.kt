package com.github.darderion.mundaneassignmentpolice.dtos

import com.github.darderion.mundaneassignmentpolice.entities.RulePack

data class RulePackDto(
    val id: Int,
    val name: String,
    val price: Int,
    var rules: List<RuleDto>
) {
    constructor(rulePack: RulePack): this(
        rulePack.id,
        rulePack.name,
        rulePack.price,
        rulePack.rules.map {
            RuleDto(it)
        }
    )
}
