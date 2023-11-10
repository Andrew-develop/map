package com.github.darderion.mundaneassignmentpolice.dtos

import com.github.darderion.mundaneassignmentpolice.entities.Subscription

data class SubscriptionDto(
    val id: Long?,
    val name: String,
    val price: Int,
    val projects: Int,
    val revisionsPerDay: Int,
    val presets: Int
) {
    constructor(subscription: Subscription): this(
        subscription.id,
        subscription.name,
        subscription.price,
        subscription.projects,
        subscription.revisionsPerDay,
        subscription.presets
    )
}
