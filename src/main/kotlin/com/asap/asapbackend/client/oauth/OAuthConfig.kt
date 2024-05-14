package com.asap.asapbackend.client.oauth

import com.asap.asapbackend.client.oauth.kakao.KakaoSocialLoginClient
import com.asap.asapbackend.domain.user.domain.exception.AuthException
import com.asap.asapbackend.domain.user.domain.model.Provider
import com.asap.asapbackend.domain.user.domain.service.SocialLoginHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OAuthConfig(
    private val kakaoSocialLoginClient: KakaoSocialLoginClient
) {

    @Bean
    fun socialLoginHandler() = object : SocialLoginHandler{
        override fun handle(request: SocialLoginHandler.Request): SocialLoginHandler.Response {
            val socialLoginClientResponse = when(request.provider){
                Provider.KAKAO -> kakaoSocialLoginClient.handle(SocialLoginClient.Request(request.accessToken))
                else -> throw AuthException.UnSupportedProviderException()
            }

            return SocialLoginHandler.Response(socialLoginClientResponse.socialId)
        }
    }
}