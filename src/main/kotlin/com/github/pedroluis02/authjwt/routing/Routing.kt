package com.github.pedroluis02.authjwt.routing

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/users") {
            userRoute()
        }
    }
}
