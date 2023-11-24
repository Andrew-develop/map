package com.github.darderion.mundaneassignmentpolice.dtos.user

import com.github.darderion.mundaneassignmentpolice.dtos.RoleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity

data class SecurityUser(
        val email: String,
        val password: String,
        val roles: List<RoleDto>
) {
        constructor(user: UserEntity): this(
                user.email,
                user.password,
                user.roles.map { RoleDto(it) }
        )
}
