package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class User(
    name: String,
    email: String,
    socialInfo: SocialInfo,
    phoneNumber: PhoneNumber,
    agreement: Agreement
) : BaseDateEntity() {
    init {
        phoneNumber.validate()
        agreement.validate()
    }

    val name: String = name

    val email: String = email

    val socialInfo: SocialInfo = socialInfo

    var phoneNumber: PhoneNumber = phoneNumber
        protected set

    var agreement: Agreement = agreement
        protected set
}