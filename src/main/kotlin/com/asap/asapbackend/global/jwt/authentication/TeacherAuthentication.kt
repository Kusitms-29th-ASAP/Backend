package com.asap.asapbackend.global.jwt.authentication

import com.asap.asapbackend.global.jwt.vo.Claims
import com.asap.asapbackend.global.jwt.vo.ClaimsType
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

data class TeacherAuthentication(
    private val teacher: Claims.TeacherClaims
) : AbstractAuthenticationToken(listOf(GrantedAuthority { ClaimsType.TEACHER.name })){
    override fun getCredentials(): Long {
        return teacher.teacherId
    }

    override fun getPrincipal(): Long {
        return teacher.teacherId
    }
}