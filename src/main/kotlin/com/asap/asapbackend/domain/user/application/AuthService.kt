package com.asap.asapbackend.domain.user.application

import com.asap.asapbackend.domain.user.application.dto.Logout
import com.asap.asapbackend.domain.user.application.dto.Reissue
import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.enum.Provider
import com.asap.asapbackend.domain.user.domain.service.SocialLoginHandler
import com.asap.asapbackend.domain.user.domain.service.UserReader
import com.asap.asapbackend.global.util.TransactionUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AuthService(
    private val authHandlerMap: Map<Provider, SocialLoginHandler>,
    private val userReader: UserReader
) {


    fun executeSocialLogin(socialLoginRequest: SocialLogin.Request, provider: Provider): SocialLogin.Response {
        // TODO: 지원하지 않는 소셜 로그인일 경우 예외처리 추가하기
        val socialLoginHandler = authHandlerMap[provider] ?: throw IllegalArgumentException("지원하지 않는 소셜 로그인입니다.")
        val response = socialLoginHandler.handle(SocialLoginHandler.Request(socialLoginRequest.accessToken))
        return TransactionUtils.writable { // 일부분만 트랜잭션 처리하기 위한 용도, 앞선 외부 api 호출은 트랜잭션에 포함해서는 안됨
            // TODO: 이미 가입된 사용자인지 확인하고 가입되지 않은 사용자라면 가입을 위한 token 반환
            return@writable userReader.findBySocialIdOrNull(response.socialId)?.let {
                SocialLogin.Response.Success("accessToken", "refreshToken")
            } ?: run {
                SocialLogin.Response.UnRegistered("registerToken")
            }
        }
    }

    @Transactional
    fun reissueToken(reissueRequest: Reissue.Request): Reissue.Response {
        // TODO: 토큰 재발급 처리
        return Reissue.Response("accessToken", "refreshToken")
    }

    @Transactional
    fun logout(logoutRequest: Logout.Request) {
        // TODO: 로그아웃 처리
    }
}