package com.github.pedroluis02.authjwt.repository

import com.github.pedroluis02.authjwt.model.User

class UserRepository {

    private val users = mutableListOf<User>()

    fun findAll(): List<User> = users

    fun findById(id: Long) = users.firstOrNull { it.id == id }

    fun save(user: User): User {
        val userWithId = user.copy(id = (users.size + 1).toLong())
        users.add(userWithId)
        return userWithId
    }

    fun findByUsername(username: String) = users.firstOrNull { it.username == username }
}