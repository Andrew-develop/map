package com.github.darderion.mundaneassignmentpolice.dtos.jwt

import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity

data class JwtRequest (
        val email: String,
        val password: String
)