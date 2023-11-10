package com.github.darderion.mundaneassignmentpolice.dtos

data class UserDto (
        val id: Long,
        val firstname: String,
        val lastname: String,
        val password: String,
        val email: String
)