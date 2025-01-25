package com.github.pedroluis02.authjwt.routing

import com.github.pedroluis02.authjwt.model.User
import com.github.pedroluis02.authjwt.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute(repository: UserRepository) {
    get {
        val users = repository.findAll()
        call.respond(users.map(User::toResponse))
    }

    get("/{id}") {
        val id = call.parameters["id"]?.toLong()
            ?: return@get call.respond(HttpStatusCode.BadRequest)

        val found = repository.findById(id)
            ?: return@get call.respond(HttpStatusCode.NotFound)

        call.respond(found.toResponse())
    }

    post {
        val request = call.receive<UserRequest>()
        val created = repository.save(request.toModel())
            ?: return@post call.respond(HttpStatusCode.BadRequest)

        call.respond(HttpStatusCode.Created, created.toResponse())
    }
}

private fun UserRequest.toModel() = User(
    id = 0,
    username = this.username,
    password = this.password
)

private fun User.toResponse() = UserResponse(
    id = this.id,
    username = this.username
)