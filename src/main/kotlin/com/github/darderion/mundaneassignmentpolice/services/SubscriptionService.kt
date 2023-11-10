package com.github.darderion.mundaneassignmentpolice.services

import com.github.darderion.mundaneassignmentpolice.entities.Subscription
import com.github.darderion.mundaneassignmentpolice.exceptions.NotFoundException
import com.github.darderion.mundaneassignmentpolice.repositories.SubscriptionRepository
import org.springframework.stereotype.Service

@Service
class SubscriptionService (
        private val subscriptionRepository: SubscriptionRepository
) {
    fun getBaseSubscription(): Subscription {
        return subscriptionRepository.findByName(name = "base")
    }

    fun getSubscriptionBy(id: Int): Subscription {
        return subscriptionRepository.findById(id).orElseThrow {
            NotFoundException(String.format("Подписки '%s' не существует", id))
        }
    }

    fun getAllSubscriptions(): List<Subscription> {
        return subscriptionRepository.findAll().toList()
    }
}