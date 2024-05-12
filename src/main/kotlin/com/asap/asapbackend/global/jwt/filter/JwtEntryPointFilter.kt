package com.asap.asapbackend.global.jwt.filter

import com.asap.asapbackend.global.exception.ErrorResponse
import com.asap.asapbackend.global.jwt.exception.TokenException
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private val logger = KotlinLogging.logger {}

@Component
class JwtEntryPointFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try{
            filterChain.doFilter(request, response)
        } catch (exception: TokenException) {
            logger.error { exception }
            response.apply {
                this.status = HttpServletResponse.SC_UNAUTHORIZED
                this.contentType = MediaType.APPLICATION_JSON_VALUE
                this.writer.write(objectMapper.writeValueAsString(ErrorResponse(exception.code, exception.message)))
            }
        }
    }
}