package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.SubscriptionDto
import com.github.darderion.mundaneassignmentpolice.repositories.SubscriptionRepository
import org.springframework.stereotype.Service

@Service
class SubscriptionService (
        private val subscriptionRepository: SubscriptionRepository
) {
    fun getSubscriptionBy(id: Int): SubscriptionDto {
        return subscriptionRepository.findById(id) ?: throw NoSuchElementException(
                "Error getting sub. Statement result is null."
        )
    }

    fun getAllSubscriptions(): List<SubscriptionDto> {
        return subscriptionRepository.findAll()
    }
}