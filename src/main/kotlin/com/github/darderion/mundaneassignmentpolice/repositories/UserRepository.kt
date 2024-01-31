package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.jwt.JwtRequest
import com.github.darderion.mundaneassignmentpolice.dtos.user.SecurityUser
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserDto
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.models.entities.*
import com.github.darderion.mundaneassignmentpolice.models.tables.UsersTable
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface UserRepository {
    fun insert(user: UserRequest): UserDto
    fun update(user: UserRequest, userId: Long): UserDto
    fun confirm(id: Long): UserDto?
    fun findById(id: Long): UserDto?
    fun findSecurityByEmail(email: String): SecurityUser?
    fun findByEmail(email: String): UserDto?
    fun updateSubscription(subId: Int, userId: Long): UserDto
    fun resetPassword(id: Long, password: String): UserDto
    fun delete(id: Long): Unit
}

@Repository
class UserRepositoryImpl: UserRepository {
    override fun insert(user: UserRequest): UserDto = transaction {
        val sub = SubscriptionEntity.findById(1) ?: throw NoSuchElementException("Error getting sub. Statement result is null.")
        val role = RoleEntity.findById(1) ?: throw NoSuchElementException("Error getting role. Statement result is null.")

        UserEntity.new {
            this.name = user.name
            this.password = user.password
            this.email = user.email
            this.subscription = sub
            this.roles = SizedCollection(listOf(role))
            this.presets = SizedCollection()
        }.let { UserDto(it) }
    }

    override fun update(user: UserRequest, userId: Long): UserDto = transaction {
        val userEntity = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        userEntity.name = user.name
        userEntity.email = user.email
        userEntity.password = user.password

        UserDto(userEntity)
    }

    override fun confirm(id: Long): UserDto? = transaction {
        val role = RoleEntity.findById(2) ?: throw NoSuchElementException("Error getting role. Statement result is null.")
        val user = UserEntity.findById(id) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        user.roles = SizedCollection(listOf(role))

        UserDto(user)
    }

    override fun findById(id: Long): UserDto? = transaction {
        UserEntity.findById(id)?.let { UserDto(it) }
    }

    override fun findByEmail(email: String): UserDto? = transaction {
        UserEntity.find { UsersTable.email eq email }.firstOrNull()?.let { UserDto(it) }
    }

    override fun findSecurityByEmail(email: String): SecurityUser? = transaction {
        UserEntity.find { UsersTable.email eq email }.firstOrNull()?.let { SecurityUser(it) }
    }

    override fun resetPassword(id: Long, password: String): UserDto = transaction {
        val user = UserEntity.findById(id) ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        user.password = password

        UserDto(user)
    }

    override fun updateSubscription(subId: Int, userId: Long): UserDto = transaction {
        val userEntity = UserEntity.findById(userId) ?: throw NoSuchElementException("Error getting user. Statement result is null.")
        val subEntity = SubscriptionEntity.findById(subId) ?: throw NoSuchElementException("Error getting sub. Statement result is null.")

        userEntity.subscription = subEntity

        UserDto(userEntity)
    }

    override fun delete(id: Long): Unit = transaction {
        UserEntity.findById(id)?.delete()
    }
}