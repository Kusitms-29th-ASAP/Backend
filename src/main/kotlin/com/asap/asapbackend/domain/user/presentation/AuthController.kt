package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.enum.Provider
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @PostMapping(AuthApi.V1.SOCIAL_LOGIN)
    fun socialLogin(@PathVariable provider: Provider,
                    @RequestBody socialLoginRequest: SocialLogin.Request
    ) : SocialLogin.Response{
        return SocialLogin.Response("test access token", "test refresh token")
    }
}