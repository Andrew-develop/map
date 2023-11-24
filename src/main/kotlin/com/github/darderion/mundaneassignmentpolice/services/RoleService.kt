package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.RoleDto
import com.github.darderion.mundaneassignmentpolice.repositories.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService (
        private val roleRepository: RoleRepository
) {
    fun getUserRole(): RoleDto {
        return roleRepository.findByName(name = "ROLE_USER") ?: throw NoSuchElementException(
                "Error getting role. Statement result is null."
        )
    }
}