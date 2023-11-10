package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.entities.RulePack
import com.github.darderion.mundaneassignmentpolice.exceptions.NotFoundException
import com.github.darderion.mundaneassignmentpolice.repositories.RulePackRepository
import org.springframework.stereotype.Service

@Service
class RulePackService (
    private val rulePackRepository: RulePackRepository
) {
    fun getBaseRulePacks(): List<RulePack> {
        return rulePackRepository.findAll().filter {
            it.price == 0
        }
    }

    fun getRulePackBy(id: Int): RulePack {
        return rulePackRepository.findById(id).orElseThrow {
            NotFoundException(String.format("Набора правил '%s' не существует", id))
        }
    }

    fun getAllRulePacks(): List<RulePack> {
        return rulePackRepository.findAll().toList()
    }
}