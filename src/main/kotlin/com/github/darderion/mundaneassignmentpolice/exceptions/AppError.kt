package com.github.darderion.mundaneassignmentpolice.exceptions

import java.util.Date

data class AppError (
        val status: Int,
        val message: String,
        val timestamp: Date = Date()
)
