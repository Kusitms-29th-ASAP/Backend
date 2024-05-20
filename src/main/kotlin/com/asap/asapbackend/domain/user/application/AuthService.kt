package com.asap.asapbackend.domain.user.application

import com.asap.asapbackend.domain.user.application.dto.Logout
import com.asap.asapbackend.domain.user.application.dto.Reissue
import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.model.Provider
import com.asap.asapbackend.domain.user.domain.service.SocialLoginHandler
import com.asap.asapbackend.domain.user.domain.service.UserReader
import com.asap.asapbackend.global.jwt.util.JwtProvider
import com.asap.asapbackend.global.jwt.util.JwtRegistry
import com.asap.asapbackend.global.jwt.vo.Claims
import com.asap.asapbackend.global.util.TransactionUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class AuthService(
    private val authHandler: SocialLoginHandler,
    private val userReader: UserReader,
    private val jwtProvider: JwtProvider,
    private val jwtRegistry: JwtRegistry
) {

    fun executeSocialLogin(socialLoginRequest: SocialLogin.Request, provider: Provider): SocialLogin.Response {
        val response = authHandler.handle(SocialLoginHandler.Request(provider, socialLoginRequest.accessToken))
        return TransactionUtils.writable {
            return@writable userReader.findBySocialIdOrNull(response.socialId)?.let {
                val accessToken = jwtProvider.generateAccessToken(Claims.UserClaims(it.id))
                val refreshToken = jwtProvider.generateRefreshToken(Claims.UserClaims(it.id))
                SocialLogin.Response.Success(accessToken, refreshToken)
            } ?: run {
                val registrationToken =
                    jwtProvider.generateRegistrationToken(Claims.RegistrationClaims(
                        socialId = response.socialId,
                        provider=provider,
                        name = response.name,
                        email = response.email
                    ))
                SocialLogin.Response.UnRegistered(registrationToken)
            }
        }
    }

    @Transactional
    fun reissueToken(reissueRequest: Reissue.Request): Reissue.Response {
        val (accessToken, refreshToken) = jwtProvider.reissueToken(reissueRequest.refreshToken)
        return Reissue.Response(accessToken, refreshToken)
    }

    @Transactional
    fun logout(logoutRequest: Logout.Request) {
        jwtRegistry.delete(logoutRequest.refreshToken)
    }
}