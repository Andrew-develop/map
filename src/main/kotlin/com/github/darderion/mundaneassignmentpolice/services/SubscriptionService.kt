package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.subscription.SubscriptionDto
import com.github.darderion.mundaneassignmentpolice.repositories.SubscriptionRepository
import com.github.darderion.mundaneassignmentpolice.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class SubscriptionService (
        private val subscriptionRepository: SubscriptionRepository
) {
    fun getSubscriptionBy(id: Int): SubscriptionDto? {
        return subscriptionRepository.findById(id)
    }

    fun getAllSubscriptions(): List<SubscriptionDto> {
        return subscriptionRepository.findAll()
    }
}