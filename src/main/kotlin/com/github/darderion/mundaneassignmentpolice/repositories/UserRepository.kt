package com.github.darderion.mundaneassignmentpolice.repositories

import com.github.darderion.mundaneassignmentpolice.dtos.user.User
import com.github.darderion.mundaneassignmentpolice.dtos.user.UserRequest
import com.github.darderion.mundaneassignmentpolice.models.entities.*
import com.github.darderion.mundaneassignmentpolice.models.tables.UsersTable
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

interface UserRepository {
    fun insert(user: UserRequest): User
    fun update(user: UserRequest, userId: Long): User
    fun confirm(id: Long): User
    fun findById(id: Long): User?
    fun findSecurityByEmail(email: String): User?
    fun findByEmail(email: String): User?
    fun resetPassword(id: Long, password: String): User
    fun applySubscription(id: Int, userId: Long): User
    fun delete(id: Long): Unit
}

@Repository
class UserRepositoryImpl: UserRepository {
    override fun insert(user: UserRequest): User = transaction {
        val sub = SubscriptionEntity.findById(1)
                ?: throw NoSuchElementException("Error getting sub. Statement result is null.")
        val role = RoleEntity.findById(1)
                ?: throw NoSuchElementException("Error getting role. Statement result is null.")

        UserEntity.new {
            this.name = user.name
            this.password = user.password
            this.email = user.email
            this.subscription = sub
            this.roles = SizedCollection(listOf(role))
            this.presets = SizedCollection()
        }.let { User(it) }
    }

    override fun update(user: UserRequest, userId: Long): User = transaction {
        val userEntity = UserEntity.findById(userId)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        userEntity.name = user.name
        userEntity.email = user.email
        userEntity.password = user.password

        User(userEntity)
    }

    override fun confirm(id: Long): User = transaction {
        val role = RoleEntity.findById(2)
                ?: throw NoSuchElementException("Error getting role. Statement result is null.")
        val user = UserEntity.findById(id)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        user.roles = SizedCollection(listOf(role))

        User(user)
    }

    override fun findById(id: Long): User? = transaction {
        UserEntity.findById(id)?.let { User(it) }
    }

    override fun findByEmail(email: String): User? = transaction {
        UserEntity.find { UsersTable.email eq email }.firstOrNull()?.let { User(it) }
    }

    override fun findSecurityByEmail(email: String): User? = transaction {
        UserEntity.find { UsersTable.email eq email }.firstOrNull()?.let { User(it) }
    }

    override fun resetPassword(id: Long, password: String): User = transaction {
        val user = UserEntity.findById(id)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        user.password = password

        User(user)
    }

    override fun applySubscription(id: Int, userId: Long): User = transaction {
        val sub = SubscriptionEntity.findById(id)
                ?: throw NoSuchElementException("Error getting role. Statement result is null.")
        val user = UserEntity.findById(userId)
                ?: throw NoSuchElementException("Error getting user. Statement result is null.")

        user.subscription = sub

        User(user)
    }

    override fun delete(id: Long): Unit = transaction {
        UserEntity.findById(id)?.delete()
    }
}