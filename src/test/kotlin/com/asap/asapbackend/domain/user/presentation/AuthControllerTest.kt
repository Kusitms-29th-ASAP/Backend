package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.enum.Provider
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AuthController::class)
class AuthControllerTest : AbstractRestDocsConfigurer() {

    @Test
    @DisplayName("소셜 로그인")
    fun socialLogin() {
        //given
        val provider = Provider.KAKAO
        val requestAccessToken = "accessToken"
        val requestRefreshToken = "refreshToken"
        val socialLoginRequest = SocialLogin.Request(requestAccessToken, requestRefreshToken)
        val request = RestDocumentationRequestBuilders.post(AuthApi.V1.SOCIAL_LOGIN, provider)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(socialLoginRequest))
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("accessToken").description("소셜 로그인 플랫폼에서 발급받은 accessToken"),
                        fieldWithPath("refreshToken").description("소셜 로그인 플랫폼에서 발급받은 refreshToken")
                    ),
                    pathParameters(
                        parameterWithName("provider").description("소셜 로그인 플랫폼(GOOGLE, KAKAO)")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("서버 접근을 위한 accessToken"),
                        fieldWithPath("refreshToken").description("서버 접근을 위한 refreshToken")
                    )
                )

            )
    }
}