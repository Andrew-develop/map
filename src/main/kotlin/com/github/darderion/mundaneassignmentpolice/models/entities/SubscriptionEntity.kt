package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.SubscriptionsTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SubscriptionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<SubscriptionEntity>(SubscriptionsTable)

    var name by SubscriptionsTable.name
}