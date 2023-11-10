package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.entities.Role
import com.github.darderion.mundaneassignmentpolice.repositories.RoleRepository
import org.springframework.stereotype.Service

@Service
class RoleService (
        private val roleRepository: RoleRepository
) {
    fun getUserRole(): Role {
        return roleRepository.findByName(name = "ROLE_USER")
    }
}