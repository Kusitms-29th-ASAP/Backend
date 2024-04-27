package com.asap.asapbackend.global.security.jwt

data class Token(
    val accessToken: String,
    val refreshToken: String
)
