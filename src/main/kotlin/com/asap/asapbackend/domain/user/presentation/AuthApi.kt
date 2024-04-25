package com.asap.asapbackend.domain.user.presentation

object AuthApi {

    object V1{
        const val BASE_URL = "/api/v1/auth"

        const val SOCIAL_LOGIN = "$BASE_URL/login/{provider}"
        const val LOGOUT = "$BASE_URL/logout"

        const val REISSUE = "$BASE_URL/reissue"
    }

}