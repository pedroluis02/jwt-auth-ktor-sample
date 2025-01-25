package com.github.pedroluis02.authjwt.plugins

import com.github.pedroluis02.authjwt.service.JwtService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(service: JwtService) {
    authentication {
        jwt {
            realm = service.properties.realm

            verifier(service.verifier)

            validate { credential ->
                service.validate(credential)
            }
        }
    }
}
