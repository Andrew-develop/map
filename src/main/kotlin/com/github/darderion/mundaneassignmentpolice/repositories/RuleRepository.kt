package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.RuleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.RuleEntity
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface RuleRepository {
    fun findAll(): List<RuleDto>
}

@Repository
class RuleRepositoryImpl: RuleRepository {
    override fun findAll(): List<RuleDto> = transaction {
        RuleEntity.all().toList().map {
            RuleDto(it.id.value, it.name)
        }
    }
}