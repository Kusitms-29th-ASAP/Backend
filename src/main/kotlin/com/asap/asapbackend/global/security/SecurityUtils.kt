package com.asap.asapbackend.global.security

import com.asap.asapbackend.global.jwt.vo.ClaimsType
import com.asap.asapbackend.global.security.exception.AuthenticationNotFouncException
import com.asap.asapbackend.global.security.exception.SecurityErrorCode
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder


fun getCurrentUserId(): Long {
    return getCurrentAuthenticationPrincipal(ClaimsType.USER)
}

fun getTeacherId(): Long {
    return getCurrentAuthenticationPrincipal(ClaimsType.TEACHER)
}

private fun getCurrentAuthenticationPrincipal(claimsType: ClaimsType): Long {
    val authentication = SecurityContextHolder.getContext().authentication

    return authentication?.let {
        if (it.authorities.contains(GrantedAuthority { claimsType.name })) {
            return@let it.principal as Long
        }
        return@let null
    } ?: throw AuthenticationNotFouncException(SecurityErrorCode.AUTHENTICATION_NOT_FOUND)
}