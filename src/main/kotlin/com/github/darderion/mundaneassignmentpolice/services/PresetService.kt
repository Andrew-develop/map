package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.entities.Preset
import com.github.darderion.mundaneassignmentpolice.entities.Rule
import com.github.darderion.mundaneassignmentpolice.entities.User
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.exceptions.NotFoundException
import com.github.darderion.mundaneassignmentpolice.repositories.PresetRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PresetService (
    private val presetRepository: PresetRepository
) {
    fun getUserPresets(user: User): ResponseEntity<*> {
        return ResponseEntity.ok(presetRepository.findByUser(user).map {
            PresetDto(it)
        })
    }

    fun createNewPreset(name: String, user: User, rules: List<Rule>) {
        val availableRules = getAvailableRules(user)
        val preset = Preset(
            name = name,
            user = user,
            rules = rules.filter {
                availableRules.contains(it)
            }
        )
        presetRepository.save(preset)
    }

    @Transactional
    fun updatePreset(id: Long, name: String, user: User, rules: List<Rule>): ResponseEntity<*> {
        val availableRules = getAvailableRules(user)
        val preset = presetRepository.findById(id).orElseThrow {
            NotFoundException(String.format("Набора '%s' не существует", id))
        }
        if (preset.user == user) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "У пользователя нет набора" + id), HttpStatus.BAD_REQUEST)
        }
        preset.name = name
        preset.rules = rules.filter {
            availableRules.contains(it)
        }
        presetRepository.save(preset)
        return ResponseEntity.ok("preset successfully updated")
    }

    @Transactional
    fun deletePresetBy(id: Long, user: User): ResponseEntity<*> {
        val preset = presetRepository.findById(id).orElseThrow {
            NotFoundException(String.format("Набора '%s' не существует", id))
        }
        if (preset.user == user) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "У пользователя нет набора" + id), HttpStatus.BAD_REQUEST)
        }
        presetRepository.delete(preset)
        return ResponseEntity.ok("preset successfully deleted")
    }

    private fun getAvailableRules(user: User): List<Rule> {
        var rules: List<Rule> = emptyList()
        user.rulePacks.forEach {
            rules += it.rules
        }
        return rules
    }
}