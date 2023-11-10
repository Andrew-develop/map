package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.entities.Rule
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RuleRepository: CrudRepository<Rule, Int> {
}