package com.asap.asapbackend.domain.user.application.dto

class GetUser {
    data class Response(
        val userName: String,
        val phoneNumber: String,
        val email: String
    )
}