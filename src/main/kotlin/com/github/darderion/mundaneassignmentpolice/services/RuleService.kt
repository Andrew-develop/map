package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.RuleDto
import com.github.darderion.mundaneassignmentpolice.repositories.RuleRepository
import org.springframework.stereotype.Service

@Service
class RuleService (
    private val ruleRepository: RuleRepository
) {
    fun getAll(): List<RuleDto> {
        return ruleRepository.findAll()
    }
}