package com.github.darderion.mundaneassignmentpolice.dtos.user

import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity

data class UserDto (
        val id: Long,
        val name: String,
        val email: String,
        val subscriptionId: Int
) {
        constructor(user: User): this(
                user.id,
                user.name,
                user.email,
                user.subscriptionId
        )
}