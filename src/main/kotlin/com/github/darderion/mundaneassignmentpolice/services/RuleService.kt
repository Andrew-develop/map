package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.repositories.RuleRepository
import com.github.darderion.mundaneassignmentpolice.rules.*
import org.springframework.stereotype.Service
import com.github.darderion.mundaneassignmentpolice.checker.rule.Rule
import com.github.darderion.mundaneassignmentpolice.dtos.rule.RuleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.RuleEntity

@Service
class RuleService (
    private val ruleRepository: RuleRepository
) {
    fun getAll(): List<RuleDto> {
        return ruleRepository.findAll()
    }

    fun convertToSet(rules: List<RuleDto>): RuleSet {
        return RuleSet(
            rules.mapNotNull {
                getRuleBy(it.name)
            }
        )
    }

    private fun getRuleBy(name: String): Rule? {
        when (name) {
            "RULE_TWO_IDENTICAL_WORDS" -> return RULE_TWO_IDENTICAL_WORDS
            "RULE_OUTSIDE_FIELDS" -> return RULE_OUTSIDE_FIELDS
            "RULE_LITLINK" -> return RULE_LITLINK
            "RULE_SHORT_DASH" -> return RULE_SHORT_DASH
            "RULE_MEDIUM_DASH" -> return RULE_MEDIUM_DASH
            "RULE_LONG_DASH" -> return RULE_LONG_DASH
            "RULE_UNSCIENTIFIC_SENTENCE" -> return RULE_UNSCIENTIFIC_SENTENCE
            "RULE_CLOSING_QUOTATION" -> return RULE_CLOSING_QUOTATION
            "RULE_OPENING_QUOTATION" -> return RULE_OPENING_QUOTATION
            "RULE_MULTIPLE_LITLINKS" -> return RULE_MULTIPLE_LITLINKS
            "RULE_BRACKETS_LETTERS" -> return RULE_BRACKETS_LETTERS
            "RULE_CITATION" -> return RULE_CITATION
            "RULE_NO_TASKS" -> return RULE_NO_TASKS
            "RULE_TASKS_MAPPING" -> return RULE_TASKS_MAPPING
            "RULE_LONG_SENTENCE" -> return RULE_LONG_SENTENCE
            "RULE_SECTION_NUMBERING_FROM_0" -> return RULE_SECTION_NUMBERING_FROM_0
            "RULE_SINGLE_SUBSECTION" -> return RULE_SINGLE_SUBSECTION
            "RULE_TABLE_OF_CONTENT_NUMBERS" -> return RULE_TABLE_OF_CONTENT_NUMBERS
            "RULE_SYMBOLS_IN_SECTION_NAMES" -> return RULE_SYMBOLS_IN_SECTION_NAMES
            "RULE_DISALLOWED_WORDS" -> return RULE_DISALLOWED_WORDS
            "RULE_INCORRECT_ABBREVIATION" -> return RULE_INCORRECT_ABBREVIATION
            "RULE_SHORTENED_URLS" -> return RULE_SHORTENED_URLS
            "RULE_URLS_UNIFORMITY" -> return RULE_URLS_UNIFORMITY
            "RULE_ORDER_OF_REFERENCES" -> return RULE_ORDER_OF_REFERENCES
            "RULE_VARIOUS_ABBREVIATIONS" -> return RULE_VARIOUS_ABBREVIATIONS
            "RULE_SECTIONS_ORDER" -> return RULE_SECTIONS_ORDER
            "RULE_LOW_QUALITY_CONFERENCES" -> return RULE_LOW_QUALITY_CONFERENCES
            "RULE_NO_SPACE_AFTER_PUNCTUATION" -> return RULE_NO_SPACE_AFTER_PUNCTUATION
            else -> return null
        }
    }
}