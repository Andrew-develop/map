package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.entities.Preset
import com.github.darderion.mundaneassignmentpolice.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PresetRepository: CrudRepository<Preset, Long> {
    fun findByUser(user: User): List<Preset>
}