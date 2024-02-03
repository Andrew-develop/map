package com.github.darderion.mundaneassignmentpolice.dtos.subscription

import com.github.darderion.mundaneassignmentpolice.models.entities.SubscriptionEntity

data class SubscriptionDto(
    val id: Int,
    val name: String,
    val price: Int,
    val projects: Int,
    val revisionsPerDay: Int,
    val presets: Int
) {
    constructor(subscription: SubscriptionEntity): this(
        subscription.id.value,
        subscription.name,
        0,
        0,
        0,
        0
    )
}
