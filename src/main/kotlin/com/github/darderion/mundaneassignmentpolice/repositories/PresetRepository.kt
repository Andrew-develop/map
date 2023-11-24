package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.models.entities.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface PresetRepository {
    fun findByIds(ids: List<Long>): List<PresetDto>
    fun findByUser(id: Long): List<PresetDto>
    fun findById(id: Long): PresetDto?
    fun insert(preset: PresetRequest, userId: Long): PresetDto
    fun update(preset: PresetRequest, presetId: Long): PresetDto
    fun delete(id: Long): Unit
}

@Repository
class PresetRepositoryImpl: PresetRepository {
    override fun findByIds(ids: List<Long>): List<PresetDto> = transaction {
        PresetEntity.forIds(ids).toList().map { PresetDto(it) }
    }
    override fun findByUser(id: Long): List<PresetDto> = transaction {
        val user = UserEntity.findById(id) ?: throw NoSuchElementException("Error getting user. Statement result is null.")
        user.presets.map { PresetDto(it) }
    }

    override fun findById(id: Long): PresetDto? = transaction {
        PresetEntity.findById(id)?.let { PresetDto(it) }
    }

    override fun insert(preset: PresetRequest, userId: Long): PresetDto = transaction {
        val ruleEntities = RuleEntity.forIds(preset.rules).toList()
        val userEntity = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        PresetEntity.new {
            this.name = preset.name
            this.rules = SizedCollection(ruleEntities)
            this.users = SizedCollection(listOf(userEntity))
        }.let { PresetDto(it) }
    }

    override fun update(preset: PresetRequest, presetId: Long): PresetDto = transaction {
        val presetEntity = PresetEntity.findById(presetId) ?: throw NoSuchElementException("Error getting preset. Statement result is null.")
        val ruleEntities = RuleEntity.forIds(preset.rules).toList()

        presetEntity.name = preset.name
        presetEntity.rules = SizedCollection(ruleEntities)

        PresetDto(presetEntity)
    }

    override fun delete(id: Long): Unit = transaction {
        PresetEntity.findById(id)?.delete()
    }
}