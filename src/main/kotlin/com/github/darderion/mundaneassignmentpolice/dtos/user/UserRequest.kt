package com.github.darderion.mundaneassignmentpolice.dtos.user

data class UserRequest(
        val name: String,
        val password: String,
        val email: String
)
