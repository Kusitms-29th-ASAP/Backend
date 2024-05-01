package com.asap.asapbackend.global.jwt

import com.fasterxml.jackson.annotation.JsonProperty

class PrivateClaims(
    val userClaims: UserClaims,
    val tokenType: TokenType
) {

    companion object {
        fun retrieveClaimsClassType(): Map<String, Class<*>> = mapOf(
            JwtConst.USER_CLAIMS to UserClaims::class.java,
            JwtConst.TOKEN_TYPE to TokenType::class.java
        )
    }

    fun convertToClaims(): Map<String, Any> = mapOf(
        JwtConst.USER_CLAIMS to userClaims,
        JwtConst.TOKEN_TYPE to tokenType
    )


    class UserClaims(
        @JsonProperty("user_id") val userId: Long
    ) {
        fun createPrivateClaims(tokenType: TokenType) = PrivateClaims(this, tokenType)
    }
}