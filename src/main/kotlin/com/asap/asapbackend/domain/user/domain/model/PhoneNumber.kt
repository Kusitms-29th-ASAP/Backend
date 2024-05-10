package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.global.exception.validateProperty
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
        validateProperty(regex.matches(number))
    }
}