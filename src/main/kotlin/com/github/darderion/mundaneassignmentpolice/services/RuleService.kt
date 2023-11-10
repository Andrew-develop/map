package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.entities.Rule
import com.github.darderion.mundaneassignmentpolice.exceptions.NotFoundException
import com.github.darderion.mundaneassignmentpolice.repositories.RuleRepository
import org.springframework.stereotype.Service

@Service
class RuleService (
    private val ruleRepository: RuleRepository
) {
    fun getRuleBy(ids: List<Int>): List<Rule> {
        return ids.map {
            ruleRepository.findById(it).orElseThrow {
                NotFoundException(String.format("Правила '%s' не существует", it))
            }
        }
    }
}