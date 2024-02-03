package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.subscription.SubscriptionDto
import com.github.darderion.mundaneassignmentpolice.models.entities.SubscriptionEntity
import com.github.darderion.mundaneassignmentpolice.models.tables.SubscriptionsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface SubscriptionRepository {
    fun findByName(name: String): SubscriptionDto?
    fun findById(id: Int): SubscriptionDto?
    fun findAll(): List<SubscriptionDto>
}

@Repository
class SubscriptionRepositoryImpl: SubscriptionRepository {
    override fun findByName(name: String): SubscriptionDto? = transaction {
        SubscriptionEntity.find { SubscriptionsTable.name eq name }.firstOrNull()?.let {
            SubscriptionDto(it.id.value, it.name, 0, 0, 0, 0)
        }
    }

    override fun findById(id: Int): SubscriptionDto? = transaction {
        SubscriptionEntity.findById(id)?.let {
            SubscriptionDto(it.id.value, it.name, 0, 0, 0, 0)
        }
    }

    override fun findAll(): List<SubscriptionDto> = transaction {
        SubscriptionEntity.all().toList().map {
            SubscriptionDto(it.id.value, it.name, 0, 0, 0, 0)
        }
    }
}