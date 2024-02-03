package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.dtos.subscription.SubscriptionDto
import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.models.entities.SubscriptionEntity
import com.github.darderion.mundaneassignmentpolice.models.entities.UserEntity
import com.github.darderion.mundaneassignmentpolice.repositories.SubscriptionRepository
import com.github.darderion.mundaneassignmentpolice.repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class SubscriptionService (
        private val subscriptionRepository: SubscriptionRepository,
        private val userRepository: UserRepository
) {
    fun getSubscriptionBy(id: Int): SubscriptionDto? {
        return subscriptionRepository.findById(id)
    }

    fun getAllSubscriptions(): List<SubscriptionDto> {
        return subscriptionRepository.findAll()
    }
}