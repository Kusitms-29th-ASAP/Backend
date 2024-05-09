package com.asap.asapbackend.global.jwt.filter

import com.asap.asapbackend.global.jwt.authentication.TeacherAuthentication
import com.asap.asapbackend.global.jwt.authentication.UserAuthentication
import com.asap.asapbackend.global.jwt.exception.InvalidTokenException
import com.asap.asapbackend.global.jwt.exception.TokenErrorCode
import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.util.TokenExtractor
import com.asap.asapbackend.global.jwt.vo.Claims
import com.asap.asapbackend.global.jwt.vo.ClaimsType
import com.asap.asapbackend.global.jwt.vo.TokenType
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenExtractor: TokenExtractor,
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter(){


    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenHeaderValue = request.getHeader(TokenExtractor.Constants.HEADER)
        tokenHeaderValue?.let {
            val tokenValue = tokenExtractor.extractValue(it)
            val claimsType = jwtProvider.extractClaimsTypeFromToken(tokenValue, TokenType.ACCESS_TOKEN)
            val claims = when(claimsType){
                ClaimsType.USER -> jwtProvider.extractUserClaimsFromToken(tokenValue, TokenType.ACCESS_TOKEN)
                ClaimsType.TEACHER -> jwtProvider.extractTeacherClaimsFromToken(tokenValue, TokenType.ACCESS_TOKEN)
                else -> throw InvalidTokenException(TokenErrorCode.INVALID_TOKEN)
            }

            SecurityContextHolder.getContext().authentication =when(claims){
                is Claims.UserClaims -> UserAuthentication(claims)
                is Claims.TeacherClaims -> TeacherAuthentication(claims)
                else -> throw InvalidTokenException(TokenErrorCode.INVALID_TOKEN)
            }
        }
        filterChain.doFilter(request, response)
    }

}