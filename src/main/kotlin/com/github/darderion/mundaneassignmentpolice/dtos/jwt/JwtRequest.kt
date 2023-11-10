package com.github.darderion.mundaneassignmentpolice.dtos.jwt

data class JwtRequest (
        val email: String,
        val password: String
)