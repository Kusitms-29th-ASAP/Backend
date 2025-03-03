package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity

@Entity
class User(
    socialInfo: SocialInfo,
    phoneNumber: PhoneNumber,
    agreement: Agreement,
    name: String,
    email: String
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

    var name: String = name
        protected set

    var email: String = email
        protected set

    fun changeUserInfo(name: String,phoneNumber: PhoneNumber){
        this.name=name
        this.phoneNumber=phoneNumber
    }
}