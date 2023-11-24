package com.github.darderion.mundaneassignmentpolice.models.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsersRolesTable : Table("users_roles") {
    val userId = reference("user_id", UsersTable, ReferenceOption.CASCADE)
    val roleId = reference("role_id", RolesTable, ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(userId, roleId)
}