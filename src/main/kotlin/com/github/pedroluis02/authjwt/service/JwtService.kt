package com.github.pedroluis02.authjwt.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.pedroluis02.authjwt.model.UserLogin
import com.github.pedroluis02.authjwt.repository.UserRepository
import io.ktor.server.config.*
import java.util.*

class JwtService(
    private val config: ApplicationConfig,
    private val repository: UserRepository
) {

    private val properties = loadProperties()

    fun create(model: UserLogin): String? {
        println(properties)
        val user = repository.findByUsername(model.username)
        if (user == null || user.password != model.password)
            return null

        return JWT.create()
            .withAudience(properties.audience)
            .withIssuer(properties.domain)
            .withClaim("username", user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 3_600_000))
            .sign(Algorithm.HMAC256(properties.secret))
    }

    private fun loadProperties() = JwtProperties(
        domain = getPropertyValue("domain"),
        audience = getPropertyValue("audience"),
        realm = getPropertyValue("realm"),
        secret = getPropertyValue("secret")
    )

    private fun getPropertyValue(name: String) = config.property("jwt.$name").getString()
}