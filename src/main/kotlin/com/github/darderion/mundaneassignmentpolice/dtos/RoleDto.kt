package com.github.darderion.mundaneassignmentpolice.dtos

import com.github.darderion.mundaneassignmentpolice.models.entities.RoleEntity

data class RoleDto(
        val id: Int,
        val name: String
) {
    constructor(role: RoleEntity): this(
            role.id.value,
            role.name
    )
}
