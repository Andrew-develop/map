package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.entities.RulePack
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RulePackRepository: CrudRepository<RulePack, Int> {
}