package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.repositories.PresetRepository
import org.springframework.stereotype.Service

@Service
class PresetService (
    private val presetRepository: PresetRepository
) {
    fun getPresetBy(id: Long): PresetDto? {
        return presetRepository.findById(id)
    }

    fun getUserPresets(id: Long): List<PresetDto> {
        return presetRepository.findByUser(id)
    }

    fun createPreset(request: PresetRequest, user: User): PresetDto {
        return presetRepository.insert(request, user.id)
    }

    fun updatePreset(request: PresetRequest, id: Long, user: User): PresetDto? {
        val preset = presetRepository.findById(id) ?: return null
        return if (preset.ownerId == user.id)
            presetRepository.update(request, id) else null
    }

    fun deletePresetBy(id: Long, user: User): Unit? {
        val preset = presetRepository.findById(id) ?: return null
        return if (preset.ownerId == user.id)
            presetRepository.delete(id) else null
    }
}