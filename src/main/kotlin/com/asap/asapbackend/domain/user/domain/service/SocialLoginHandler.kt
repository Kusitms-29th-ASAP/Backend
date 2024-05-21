package com.asap.asapbackend.domain.user.domain.service

import com.asap.asapbackend.domain.user.domain.exception.AuthException
import com.asap.asapbackend.domain.user.domain.model.Provider

interface SocialLoginHandler{

    @Throws(AuthException.AuthFailedException::class, AuthException.UnSupportedProviderException::class)
    fun handle(request: Request): Response

    data class Request(
        val provider: Provider,
        val accessToken: String
    )

    data class Response(
        val socialId: String,
        val email: String,
        val name: String,
    )
}