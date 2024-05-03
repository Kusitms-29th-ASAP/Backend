package com.asap.asapbackend.domain.user.application

import com.asap.asapbackend.domain.user.application.dto.Logout
import com.asap.asapbackend.domain.user.application.dto.Reissue
import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.model.Provider
import com.asap.asapbackend.domain.user.domain.service.SocialLoginHandler
import com.asap.asapbackend.domain.user.domain.service.UserReader
import com.asap.asapbackend.global.jwt.Claims
import com.asap.asapbackend.global.jwt.JwtProvider
import com.asap.asapbackend.global.jwt.JwtRegistry
import com.asap.asapbackend.global.util.TransactionUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class AuthService(
    private val authHandlerMap: Map<Provider, SocialLoginHandler>,
    private val userReader: UserReader,
    private val jwtProvider: JwtProvider,
    private val jwtRegistry: JwtRegistry
) {

    fun executeSocialLogin(socialLoginRequest: SocialLogin.Request, provider: Provider): SocialLogin.Response {
        val socialLoginHandler = authHandlerMap[provider] ?: throw IllegalArgumentException("지원하지 않는 소셜 로그인입니다.")
        val response = socialLoginHandler.handle(SocialLoginHandler.Request(socialLoginRequest.accessToken))
        return TransactionUtils.writable { // 일부분만 트랜잭션 처리하기 위한 용도, 앞선 외부 api 호출은 트랜잭션에 포함해서는 안됨
            return@writable userReader.findBySocialIdOrNull(response.socialId)?.let {
                val accessToken = jwtProvider.generateAccessToken(Claims.UserClaims(it.id))
                val refreshToken = jwtProvider.generateRefreshToken(Claims.UserClaims(it.id))
                SocialLogin.Response.Success(accessToken, refreshToken)
            } ?: run {
                val registrationToken = jwtProvider.generateRegistrationToken(Claims.RegistrationClaims(response.socialId, provider))
                SocialLogin.Response.UnRegistered(registrationToken)
            }
        }
    }

    @Transactional
    fun reissueToken(reissueRequest: Reissue.Request): Reissue.Response {
        val reissueToken = jwtProvider.reissueToken(reissueRequest.refreshToken)
        return Reissue.Response(reissueToken.accessToken, reissueToken.refreshToken)
    }

    @Transactional
    fun logout(logoutRequest: Logout.Request) {
        jwtRegistry.delete(logoutRequest.refreshToken)
    }
}