package com.github.pedroluis02.authjwt.service

import com.github.pedroluis02.authjwt.model.User
import com.github.pedroluis02.authjwt.repository.UserRepository

class UserService(private val repository: UserRepository = UserRepository()) {

    fun findAll() = repository.findAll()

    fun findById(id: Long) = repository.findById(id)

    fun save(user: User): User? {
        val found = repository.findByUsername(user.username)

        return if (found == null) {
            repository.save(user)
        } else null
    }

    fun findByUsername(username: String) = repository.findByUsername(username)
}