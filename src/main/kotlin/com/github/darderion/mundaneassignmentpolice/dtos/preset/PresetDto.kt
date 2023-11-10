package com.github.darderion.mundaneassignmentpolice.dtos.preset

import com.github.darderion.mundaneassignmentpolice.dtos.RuleDto
import com.github.darderion.mundaneassignmentpolice.entities.Preset

data class PresetDto(
    val id: Long?,
    val name: String,
    val rules: List<RuleDto>
) {
    constructor(preset: Preset): this(
        preset.id,
        preset.name,
        preset.rules.map {
            RuleDto(it.id, it.name)
        }
    )
}
