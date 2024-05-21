package com.asap.asapbackend.domain.user.application.dto

import com.asap.asapbackend.domain.user.domain.model.PhoneNumber

class ChangeUserInfo {
    data class Request(
        val userName: String,
        val phoneNumber: PhoneNumber
    )
}