package com.github.pedroluis02.authjwt.routing

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Long,
    val username: String
)
