package com.github.darderion.mundaneassignmentpolice.dtos.user

import com.github.darderion.mundaneassignmentpolice.dtos.RoleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity

data class User(
        val id: Long,
        val name: String,
        val email: String,
        val password: String,
        val subscriptionId: Int,
        val roles: List<RoleDto>
) {
        constructor(user: UserEntity): this(
                user.id.value,
                user.name,
                user.email,
                user.password,
                user.subscription.id.value,
                user.roles.map { RoleDto(it) }
        )
}
