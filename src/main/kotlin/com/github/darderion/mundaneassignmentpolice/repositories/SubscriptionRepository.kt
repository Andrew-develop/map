package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.entities.Role
import com.github.darderion.mundaneassignmentpolice.entities.Subscription
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository: CrudRepository<Subscription, Int> {
    fun findByName(name: String): Subscription
}