package com.github.pedroluis02.authjwt.routing

import com.github.pedroluis02.authjwt.service.JwtService
import com.github.pedroluis02.authjwt.service.UserService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userService: UserService, jwtService: JwtService) {
    routing {
        route("/auth") {
            authRoute(jwtService)
        }

        route("/users") {
            userRoute(userService)
        }
    }
}
