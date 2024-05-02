package com.asap.asapbackend.client.oauth

import com.asap.asapbackend.client.oauth.kakao.KakaoSocialLoginHandler
import com.asap.asapbackend.domain.user.domain.model.Provider
import com.asap.asapbackend.domain.user.domain.service.SocialLoginHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OAuthConfig(
    private val kakaoSocialLoginHandler: KakaoSocialLoginHandler
) {

    @Bean
    fun socialLoginHandlerMap(): Map<Provider, SocialLoginHandler>{
        return mapOf(
            kakaoSocialLoginHandler.provider to kakaoSocialLoginHandler
        )
    }
}