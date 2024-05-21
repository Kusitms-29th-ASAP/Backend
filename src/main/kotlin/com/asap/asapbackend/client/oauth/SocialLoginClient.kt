package com.asap.asapbackend.client.oauth

import com.asap.asapbackend.domain.user.domain.model.Provider

interface SocialLoginClient {
    val provider: Provider

    fun handle(request: Request) : Response

    data class Request(
        val accessToken: String
    )


    data class Response(
        val socialId: String,
        val email: String,
        val name: String
    )

}