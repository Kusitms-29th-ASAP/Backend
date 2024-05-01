package com.asap.asapbackend.global.jwt

data class Token(
    val accessToken: String,
    val refreshToken: String
)
