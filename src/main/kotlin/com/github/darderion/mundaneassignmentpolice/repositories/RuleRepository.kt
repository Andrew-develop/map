package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.RuleDto
import com.github.darderion.mundaneassignmentpolice.dtos.preset.PresetDto
import com.github.darderion.mundaneassignmentpolice.models.entities.PresetEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.RuleEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface RuleRepository {
    fun findAll(): List<RuleDto>
    fun findByPreset(id: Long): List<RuleDto>
}

@Repository
class RuleRepositoryImpl: RuleRepository {
    override fun findAll(): List<RuleDto> = transaction {
        RuleEntity.all().toList().map {
            RuleDto(it.id.value, it.name)
        }
    }

    override fun findByPreset(id: Long): List<RuleDto> = transaction {
        val preset = PresetEntity.findById(id) ?: throw NoSuchElementException("Error getting preset. Statement result is null.")
        preset.rules.map { RuleDto(it.id.value, it.name) }
    }
}