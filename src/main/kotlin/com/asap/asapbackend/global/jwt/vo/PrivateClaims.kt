package com.asap.asapbackend.global.jwt.vo

import com.asap.asapbackend.domain.user.domain.model.Provider
import com.fasterxml.jackson.annotation.JsonProperty


class PrivateClaims(
    val claims: Claims,
    private val tokenType: TokenType,
    private val claimsType: ClaimsType = when(claims){
        is Claims.UserClaims -> ClaimsType.USER
        is Claims.TeacherClaims -> ClaimsType.TEACHER
        is Claims.RegistrationClaims -> ClaimsType.REGISTRATION
    }
) {

    companion object : ClaimsClassTypeProvider {
        override fun retrieveClaimsClassType(): Map<String, Class<*>> = mapOf(
            JwtConst.TOKEN_TYPE to TokenType::class.java,
            JwtConst.CLAIMS_TYPE to ClaimsType::class.java
        ).plus(Claims.UserClaims.retrieveClaimsClassType())
            .plus(Claims.RegistrationClaims.retrieveClaimsClassType())
            .plus(Claims.TeacherClaims.retrieveClaimsClassType())
    }

    fun convertToClaims(): Map<String, Any> = mapOf(
        JwtConst.TOKEN_TYPE to tokenType,
        JwtConst.CLAIMS_TYPE to claimsType
    ).plus(this.claims.convertToClaims())

}

sealed interface Claims {
    fun createPrivateClaims(tokenType: TokenType): PrivateClaims
    fun convertToClaims(): Map<String, Any>

    data class RegistrationClaims(
        @param:JsonProperty("social_id")
        @get:JsonProperty("social_id")
        val socialId: String,
        @param:JsonProperty("provider")
        val provider: Provider,
        @param:JsonProperty("name")
        val name: String,
        @param:JsonProperty("email")
        val email: String
    ) : Claims {
        override fun createPrivateClaims(tokenType: TokenType) = PrivateClaims(this, tokenType)
        override fun convertToClaims(): Map<String, Any> = mapOf(
            ClaimsType.REGISTRATION.claimsKey to this,
        )

        companion object : ClaimsClassTypeProvider {
            override fun retrieveClaimsClassType(): Map<String, Class<*>> {
                return mapOf(
                    ClaimsType.REGISTRATION.claimsKey to RegistrationClaims::class.java,
                )
            }
        }
    }

    data class UserClaims(
        @param:JsonProperty("user_id")
        @get:JsonProperty("user_id")
        val userId: Long
    ) : Claims {
        override fun createPrivateClaims(tokenType: TokenType) = PrivateClaims(this, tokenType)
        override fun convertToClaims(): Map<String, Any> = mapOf(
            ClaimsType.USER.claimsKey to this
        )

        companion object : ClaimsClassTypeProvider {
            override fun retrieveClaimsClassType(): Map<String, Class<*>> {
                return mapOf(
                    ClaimsType.USER.claimsKey to UserClaims::class.java
                )
            }
        }
    }

    data class TeacherClaims(
        @param:JsonProperty("teacher_id")
        @get:JsonProperty("teacher_id")
        val teacherId: Long
    ) : Claims {
        override fun createPrivateClaims(tokenType: TokenType) = PrivateClaims(this, tokenType)
        override fun convertToClaims(): Map<String, Any> = mapOf(
            ClaimsType.TEACHER.claimsKey to this
        )

        companion object : ClaimsClassTypeProvider {
            override fun retrieveClaimsClassType(): Map<String, Class<*>> {
                return mapOf(
                    ClaimsType.TEACHER.claimsKey to TeacherClaims::class.java
                )
            }
        }
    }
}

sealed interface ClaimsClassTypeProvider {
    fun retrieveClaimsClassType(): Map<String, Class<*>>
}