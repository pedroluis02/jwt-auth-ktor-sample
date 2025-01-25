package com.github.pedroluis02.authjwt.routing

import com.github.pedroluis02.authjwt.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repository = UserRepository()

    routing {
        route("/users") {
            userRoute(repository)
        }

        route("/auth") {
            authRoute(repository)
        }
    }
}
