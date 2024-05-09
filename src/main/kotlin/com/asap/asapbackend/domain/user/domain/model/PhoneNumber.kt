package com.asap.asapbackend.domain.user.domain.model

import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    val number: String
){
    init {
        validate()
    }

    fun validate(){
        validateNumber(this.number)
    }

    private fun validateNumber(number: String){
        val regex = Regex("^[0-9]{11}$")
        require(regex.matches(number)) { "형식에 맞지 않는 전화번호입니다." }
    }
}