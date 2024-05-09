package com.asap.asapbackend.global.jwt.authentication

import com.asap.asapbackend.global.jwt.vo.Claims
import com.asap.asapbackend.global.jwt.vo.ClaimsType
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

data class UserAuthentication(
    private val user: Claims.UserClaims
) : AbstractAuthenticationToken(listOf(GrantedAuthority { ClaimsType.USER.name })) {


    override fun getCredentials(): Any {
        return user.userId
    }

    override fun getPrincipal(): Any {
        return user.userId
    }
}