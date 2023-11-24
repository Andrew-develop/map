package com.github.darderion.mundaneassignmentpolice.dtos.preset

import com.github.darderion.mundaneassignmentpolice.dtos.RuleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity

data class PresetDto(
    val id: Long,
    val name: String,
    val rules: List<RuleDto>
) {
    constructor(preset: PresetEntity): this(
            preset.id.value,
            preset.name,
            preset.rules.map { RuleDto(it) }
    )
}
