package com.asap.asapbackend.domain.user.application.dto

class Logout {

    data class Request(
        val refreshToken: String
    )

}