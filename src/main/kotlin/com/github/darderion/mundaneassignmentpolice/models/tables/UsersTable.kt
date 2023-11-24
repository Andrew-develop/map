package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object UsersTable : LongIdTable("users") {
    var name = varchar("name", 80)
    var email = varchar("email", 80)
    var password = varchar("password", 120)

    var subscriptionId = reference("subscription_id", SubscriptionsTable)

    init {
        uniqueIndex(email)
    }
}