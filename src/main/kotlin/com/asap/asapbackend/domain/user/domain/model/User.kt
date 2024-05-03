package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class User(
    val socialInfo: SocialInfo,
    val phoneNumber: PhoneNumber,
    val agreement: Agreement
) : BaseDateEntity(){
    init {
        phoneNumber.validate()
        agreement.validate()
    }
}