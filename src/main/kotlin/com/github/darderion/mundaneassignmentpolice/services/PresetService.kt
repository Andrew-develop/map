package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetResponse
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.exceptions.AppError
import com.github.darderion.mundaneassignmentpolice.repositories.PresetRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PresetService (
    private val presetRepository: PresetRepository
) {
    fun getBasePresets(): List<PresetDto> {
        return presetRepository.findByIds(listOf(1,2,3))
    }
    fun getUserPresets(user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(PresetResponse(presetRepository.findByUser(user.id)))
    }

    fun createPreset(preset: PresetRequest, user: UserDto): ResponseEntity<*> {
        return ResponseEntity.ok(presetRepository.insert(preset, user.id))
    }

    fun updatePreset(preset: PresetRequest, presetId: Long, user: UserDto): ResponseEntity<*> {
        if (user.presets.firstOrNull { it.id == presetId } == null) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have preset"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(presetRepository.update(preset, presetId))
    }

    fun deletePresetBy(id: Long, user: UserDto): ResponseEntity<*> {
        if (user.presets.firstOrNull { it.id == id } == null) {
            return ResponseEntity(AppError(HttpStatus.BAD_REQUEST.value(), "User don't have preset"), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(presetRepository.delete(id))
    }
}