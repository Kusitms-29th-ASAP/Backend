package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class User(
    socialInfo: SocialInfo,
    phoneNumber: PhoneNumber,
    agreement: Agreement
) : BaseDateEntity(){
    init {
        phoneNumber.validate()
        agreement.validate()
    }

    val socialInfo: SocialInfo = socialInfo

    var phoneNumber: PhoneNumber = phoneNumber
        protected set

    var agreement: Agreement = agreement
        protected set
}