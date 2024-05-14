package com.asap.asapbackend.client.oauth.kakao

import com.asap.asapbackend.client.oauth.SocialLoginClient
import com.asap.asapbackend.domain.user.domain.exception.AuthException
import com.asap.asapbackend.domain.user.domain.model.Provider
import org.springframework.stereotype.Component

@Component
class KakaoSocialLoginClient(
    private val kakaoOAuthClient: KakaoOAuthClient
) : SocialLoginClient {
    override val provider: Provider = Provider.KAKAO
    override fun handle(request: SocialLoginClient.Request): SocialLoginClient.Response {
        val kakaoUserInfo = kakaoOAuthClient.retrieveUserInfo(request.accessToken)
            ?: throw AuthException.AuthFailedException()
        return SocialLoginClient.Response(kakaoUserInfo.id)
    }
}