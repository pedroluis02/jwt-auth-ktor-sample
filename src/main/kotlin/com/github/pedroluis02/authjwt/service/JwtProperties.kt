package com.github.pedroluis02.authjwt.service

data class JwtProperties(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)
