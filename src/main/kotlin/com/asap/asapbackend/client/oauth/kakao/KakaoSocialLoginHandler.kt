package com.asap.asapbackend.client.oauth.kakao

import com.asap.asapbackend.domain.user.domain.model.Provider
import com.asap.asapbackend.domain.user.domain.service.SocialLoginHandler
import org.springframework.stereotype.Component

@Component
class KakaoSocialLoginHandler(
    private val kakaoOAuthClient: KakaoOAuthClient
) : SocialLoginHandler {
    override val provider: Provider = Provider.KAKAO

    override fun handle(request: SocialLoginHandler.Request): SocialLoginHandler.Response {
        val kakaoUserInfo = kakaoOAuthClient.retrieveUserInfo(request.accessToken)?: throw IllegalAccessException("카카오 사용자 정보를 가져오는데 실패했습니다.")
        return SocialLoginHandler.Response(kakaoUserInfo.id)
    }
}