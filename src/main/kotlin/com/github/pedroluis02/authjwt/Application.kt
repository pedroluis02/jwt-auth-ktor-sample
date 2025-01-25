package com.github.pedroluis02.authjwt

import com.github.pedroluis02.authjwt.plugins.configureSecurity
import com.github.pedroluis02.authjwt.plugins.configureSerialization
import com.github.pedroluis02.authjwt.routing.configureRouting
import com.github.pedroluis02.authjwt.service.JwtService
import com.github.pedroluis02.authjwt.service.UserService
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userService = UserService()
    val jwtService = JwtService(environment.config, userService)

    configureSecurity(jwtService)
    configureSerialization()
    configureRouting(userService, jwtService)
}
