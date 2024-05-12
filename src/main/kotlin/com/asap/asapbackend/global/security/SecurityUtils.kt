package com.asap.asapbackend.global.security

import com.asap.asapbackend.global.jwt.vo.ClaimsType
import com.asap.asapbackend.global.security.exception.SecurityException
import org.springframework.security.core.context.SecurityContextHolder


fun getCurrentUserId(): Long {
    return getCurrentAuthenticationPrincipal(ClaimsType.USER)
}

fun getTeacherId(): Long {
    return getCurrentAuthenticationPrincipal(ClaimsType.TEACHER)
}

private fun getCurrentAuthenticationPrincipal(claimsType: ClaimsType): Long {
    val authentication = SecurityContextHolder.getContext().authentication

    return authentication?.let { authentication ->
        authentication.authorities.firstOrNull{
            it.authority == claimsType.name
        }?.let {
            authentication.principal as Long
        }
    } ?: throw SecurityException.AuthenticationNotFoundException()
}