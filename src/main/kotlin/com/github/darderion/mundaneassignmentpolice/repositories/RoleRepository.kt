package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.RoleDto
import com.github.darderion.mundaneassignmentpolice.models.entities.RoleEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.RolesTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface RoleRepository {
    fun findByName(name: String): RoleDto?
}

@Repository
class RoleRepositoryImpl: RoleRepository {
    override fun findByName(name: String): RoleDto? = transaction {
        RoleEntity.find { RolesTable.name eq name }.firstOrNull()?.let {
            RoleDto(it.id.value, it.name)
        }
    }
}