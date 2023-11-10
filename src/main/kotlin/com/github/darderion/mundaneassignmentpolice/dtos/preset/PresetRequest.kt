package com.github.darderion.mundaneassignmentpolice.dtos.preset

data class PresetRequest(
    val name: String,
    val rules: List<Int>
)
