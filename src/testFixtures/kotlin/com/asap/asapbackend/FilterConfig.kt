package com.asap.asapbackend

import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.util.TokenExtractor
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class FilterConfig {

    @Bean
    fun tokenExtractor(): TokenExtractor {
        return mock(TokenExtractor::class.java)
    }

    @Bean
    fun tokenProvider(): JwtProvider {
        return mock(JwtProvider::class.java)
    }
}