package com.github.darderion.mundaneassignmentpolice.models.entities

import com.github.darderion.mundaneassignmentpolice.models.tables.UsersPresetsTable
import com.github.darderion.mundaneassignmentpolice.models.tables.UsersRolesTable
import com.github.darderion.mundaneassignmentpolice.models.tables.UsersTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Long>): LongEntity(id) {
    companion object : LongEntityClass<UserEntity>(UsersTable)

    var name by UsersTable.name
    var password by UsersTable.password
    var email by UsersTable.email
    var subscription by SubscriptionEntity referencedOn UsersTable.subscriptionId
    var roles by RoleEntity via UsersRolesTable
    var presets by PresetEntity via UsersPresetsTable
}
