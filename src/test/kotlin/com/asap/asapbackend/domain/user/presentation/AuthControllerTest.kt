package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.user.application.AuthService
import com.asap.asapbackend.domain.user.application.dto.Reissue
import com.asap.asapbackend.domain.user.application.dto.SocialLogin
import com.asap.asapbackend.domain.user.domain.enum.Provider
import com.asap.asapbackend.fixture.generateFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


// TODO : FixtureMonkey로 변경하기
@WebMvcTest(AuthController::class)
class AuthControllerTest : AbstractRestDocsConfigurer(){

    @MockBean
    private lateinit var authService: AuthService


    @Test
    @DisplayName("소셜 로그인시 사용자가 가입되어 있으면 accessToken, refreshToken을 반환한다.")
    fun socialLoginWithRegisterUser() {
        //given
        val provider : Provider= generateFixture()
        val requestAccessToken : String = generateFixture()
        val socialLoginRequest = SocialLogin.Request(requestAccessToken)
        val responseAccessToken : String = generateFixture()
        val responseRefreshToken : String = generateFixture()
        val socialLoginResponse = SocialLogin.Response.Success(responseAccessToken, responseRefreshToken)
        val request = RestDocumentationRequestBuilders.post(AuthApi.V1.SOCIAL_LOGIN, provider)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(socialLoginRequest))
        given(authService.executeSocialLogin(socialLoginRequest, provider)).willReturn(socialLoginResponse)
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("accessToken").description("소셜 로그인 플랫폼에서 발급받은 accessToken"),
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

    @Test
    @DisplayName("소셜 로그인시 사용자가 가입되어 있지 않으면 registerToken을 반환한다.")
    fun socialLoginWithUnRegisterUser() {
        //given
        val provider : Provider= generateFixture()
        val requestAccessToken : String = generateFixture()
        val socialLoginRequest = SocialLogin.Request(requestAccessToken)
        val responseRegisterToken : String = generateFixture()
        val socialLoginResponse = SocialLogin.Response.UnRegistered(responseRegisterToken)
        val request = RestDocumentationRequestBuilders.post(AuthApi.V1.SOCIAL_LOGIN, provider)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(socialLoginRequest))
        given(authService.executeSocialLogin(socialLoginRequest, provider)).willReturn(socialLoginResponse)
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("accessToken").description("소셜 로그인 플랫폼에서 발급받은 accessToken"),
                    ),
                    pathParameters(
                        parameterWithName("provider").description("소셜 로그인 플랫폼(GOOGLE, KAKAO)")
                    ),
                    responseFields(
                        fieldWithPath("registerToken").description("회원가입 진행을 위한 token")
                    )
                )
            )
    }

    @Test
    @DisplayName("토큰 재발급시 accessToken, refreshToken을 반환한다.")
    fun reissueToken() {
        //given
        val refreshToken : String= generateFixture()
        val reissueRequest = Reissue.Request(refreshToken)
        val reissueAccessToken : String= generateFixture()
        val reissueRefreshToken : String= generateFixture()
        val reissueResponse = Reissue.Response(reissueAccessToken, reissueRefreshToken)
        given(authService.reissueToken(reissueRequest)).willReturn(reissueResponse)
        val request = RestDocumentationRequestBuilders.put(AuthApi.V1.REISSUE)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(reissueRequest))
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("refreshToken").description("재발급을 위한 refreshToken"),
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("재발급된 accessToken"),
                        fieldWithPath("refreshToken").description("재발급된 refreshToken")
                    )
                )
            )
    }


    @Test
    @DisplayName("로그아웃 요청시 성공한다.")
    fun logout() {
        //given
        val refreshToken : String= generateFixture()
        val logoutRequest = Reissue.Request(refreshToken)
        val request = RestDocumentationRequestBuilders.delete(AuthApi.V1.LOGOUT)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(logoutRequest))
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("refreshToken").description("로그아웃을 위한 refreshToken"),
                    )
                )
            )
    }



}