package com.github.pedroluis02.authjwt.routing

import com.github.pedroluis02.authjwt.model.UserLogin
import com.github.pedroluis02.authjwt.repository.UserRepository
import com.github.pedroluis02.authjwt.service.JwtService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoute(repository: UserRepository) {
    val service = JwtService(application.environment.config, repository)

    post {
        val request = call.receive<LoginRequest>()
        val token = service.create(request.toModel())
            ?: return@post call.respond(HttpStatusCode.Unauthorized)

        call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
    }
}

private fun LoginRequest.toModel() = UserLogin(
    username = this.username,
    password = this.password
)