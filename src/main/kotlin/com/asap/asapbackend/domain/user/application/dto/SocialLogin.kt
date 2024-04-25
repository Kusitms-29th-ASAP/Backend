package com.asap.asapbackend.domain.user.application.dto

class SocialLogin{

    data class Request(
        val accessToken: String,
    )


    sealed class Response{

        data class Success(
            val accessToken: String,
            val refreshToken: String
        ): Response()

        data class UnRegistered(
            val registerToken: String
        ): Response()
    }
}
