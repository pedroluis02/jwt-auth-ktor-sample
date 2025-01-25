package com.github.pedroluis02.authjwt.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.github.pedroluis02.authjwt.model.UserLogin
import com.github.pedroluis02.authjwt.repository.UserRepository
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import java.util.*

class JwtService(
    private val config: ApplicationConfig,
    private val repository: UserRepository
) {

    val properties = loadProperties()

    val verifier = JWT
        .require(Algorithm.HMAC256(properties.secret))
        .withAudience(properties.audience)
        .withIssuer(properties.domain)
        .build()

    fun validate(credential: JWTCredential): JWTPrincipal? {
        val username = extractUsername(credential)
        val user = username?.let(repository::findByUsername)

        return user?.let {
            if (validateAudience(credential)) {
                JWTPrincipal(credential.payload)
            } else null
        }
    }

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

    private fun extractUsername(credential: JWTCredential): String? = credential.payload.getClaim("username").asString()

    private fun validateAudience(credential: JWTCredential) = credential.payload.audience.contains(properties.audience)

    private fun loadProperties() = JwtProperties(
        domain = getPropertyValue("domain"),
        audience = getPropertyValue("audience"),
        realm = getPropertyValue("realm"),
        secret = getPropertyValue("secret")
    )

    private fun getPropertyValue(name: String) = config.property("jwt.$name").getString()
}