package com.asap.asapbackend.client.oauth.kakao

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class KakaoOAuthClient {

    private val webclient = WebClient.create("https://kapi.kakao.com")

    fun retrieveUserInfo(accessToken: String): KakaoUserInfo? {
        return webclient.get()
            .uri("/v2/user/me")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .bodyToMono(KakaoUserInfo::class.java)
            .block()
    }


    data class KakaoUserInfo(
        val id: String,
        val properties: Propreties,
        val kakao_account: KakaoAccount
    ){
        fun getName(): String {
            return properties.nickname
        }

        fun getEmail(): String {
            return kakao_account.email
        }
    }

    data class Propreties(
        val nickname: String
    )

    data class KakaoAccount(
        val email: String
    )

}