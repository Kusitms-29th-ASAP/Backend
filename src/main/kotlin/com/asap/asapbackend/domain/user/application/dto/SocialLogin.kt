package com.asap.asapbackend.domain.user.application.dto

class SocialLogin{

    data class Request(
        val accessToken: String,
        val refreshToken: String
    )


    data class Response(
        val accessToken: String,
        val refreshToken: String
    )
}
