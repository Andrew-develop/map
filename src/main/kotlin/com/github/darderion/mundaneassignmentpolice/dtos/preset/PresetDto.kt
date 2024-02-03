package com.github.darderion.mundaneassignmentpolice.dtos.preset

import com.github.darderion.mundaneassignmentpolice.dtos.rule.RuleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity

data class PresetDto(
    val id: Long,
    val name: String,
    val ownerId: Long?,
    val rules: List<RuleDto>
) {
    constructor(preset: PresetEntity): this(
            preset.id.value,
            preset.name,
            preset.owner?.id?.value,
            preset.rules.map { RuleDto(it) }
    )
}
