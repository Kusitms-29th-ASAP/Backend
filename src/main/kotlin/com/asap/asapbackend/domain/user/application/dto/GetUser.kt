package com.asap.asapbackend.domain.user.application.dto

import com.asap.asapbackend.domain.user.domain.model.PhoneNumber

class GetUser {
    data class Response(
        val userName: String,
        val phoneNumber: PhoneNumber,
        val email: String
    )
}