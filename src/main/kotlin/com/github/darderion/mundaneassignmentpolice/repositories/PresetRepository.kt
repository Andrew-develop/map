package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetRequest
import com.github.darderion.mundaneassignmentpolice.models.entities.*
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface PresetRepository {
    fun findByUser(id: Long): List<PresetDto>
    fun findById(id: Long): PresetDto?
    fun insert(preset: PresetRequest, userId: Long): PresetDto
    fun update(preset: PresetRequest, presetId: Long): PresetDto
    fun delete(id: Long): Unit
}

@Repository
class PresetRepositoryImpl: PresetRepository {

    override fun findByUser(id: Long): List<PresetDto> = transaction {
        val user = UserEntity.findById(id)
                ?.load(UserEntity::presets)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")
        user.presets.map { PresetDto(it) }
    }

    override fun findById(id: Long): PresetDto? = transaction {
        PresetEntity.findById(id)
                ?.load(PresetEntity::owner, PresetEntity::rules)
                ?.let { PresetDto(it) }
    }

    override fun insert(preset: PresetRequest, userId: Long): PresetDto = transaction {
        val ruleEntities = RuleEntity.forIds(preset.rules).toList()
        val userEntity = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        PresetEntity.new {
            this.name = preset.name
            this.owner = userEntity
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