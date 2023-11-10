package com.github.darderion.mundaneassignmentpolice.dtos

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import java.sql.Timestamp

data class RevisionDto(
        var id: Long,
        val filepath: String,
        val revDate: Timestamp,
        val preset: PresetDto
)