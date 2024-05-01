package com.asap.asapbackend.domain.user.domain.service

import com.asap.asapbackend.domain.user.domain.enum.Provider

interface SocialLoginHandler{
    val provider: Provider

    fun handle(request: Request): Response

    data class Request(
        val accessToken: String
    )

    data class Response(
        val socialId: String
    )
}