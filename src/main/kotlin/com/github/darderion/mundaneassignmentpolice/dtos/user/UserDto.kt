package com.github.darderion.mundaneassignmentpolice.dtos.user

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity

data class UserDto (
        val id: Long,
        val name: String,
        val email: String,
        val subscriptionId: Int
) {
        constructor(user: UserEntity): this(
                user.id.value,
                user.name,
                user.email,
                user.subscription.id.value
        )
}