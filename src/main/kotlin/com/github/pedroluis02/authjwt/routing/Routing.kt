package com.github.pedroluis02.authjwt.routing

import com.github.pedroluis02.authjwt.repository.UserRepository
import com.github.pedroluis02.authjwt.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userRepository: UserRepository, jwtService: JwtService) {
    routing {
        route("/auth") {
            authRoute(jwtService)
        }

        route("/users") {
            userRoute(userRepository)
        }
    }
}
