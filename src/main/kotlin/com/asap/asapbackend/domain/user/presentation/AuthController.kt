package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.domain.user.application.AuthService
import com.asap.asapbackend.domain.user.application.dto.Logout
import com.asap.asapbackend.domain.user.application.dto.Reissue
import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.enum.Provider
import org.springframework.web.bind.annotation.*

@RestController
class AuthController(
    private val authService: AuthService
) {

    // 멱등성 보장하지 않기 때문에 POST
    @PostMapping(AuthApi.V1.SOCIAL_LOGIN)
    fun socialLogin(@PathVariable provider: Provider,
                    @RequestBody socialLoginRequest: SocialLogin.Request
    ) : SocialLogin.Response{
        return authService.executeSocialLogin(socialLoginRequest, provider)
    }

    // 멱등성 보장하지 않기 때문에 POST
    @PostMapping(AuthApi.V1.REISSUE)
    fun reissueToken(@RequestBody reissueRequest: Reissue.Request) : Reissue.Response{
        return authService.reissueToken(reissueRequest)
    }

    @DeleteMapping(AuthApi.V1.LOGOUT)
    fun logout(
        @RequestBody logoutRequest: Logout.Request
    ) {
        authService.logout(logoutRequest)
    }
}