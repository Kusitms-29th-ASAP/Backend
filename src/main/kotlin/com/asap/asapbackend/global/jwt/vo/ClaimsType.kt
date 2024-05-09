package com.asap.asapbackend.global.jwt.vo

enum class ClaimsType(
    val claimsKey: String
){
    USER("user_claims"),
    TEACHER("teacher_claims"),
    REGISTRATION("registration_claims")
}