package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.user.application.UserService
import com.asap.asapbackend.domain.user.application.dto.CreateUser
import com.asap.asapbackend.domain.user.domain.model.PhoneNumber
import com.asap.asapbackend.fixture.generateFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UserController::class)
class UserControllerTest: AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var userService: UserService

    @Test
    @DisplayName("유저 생성시 유저 정보를 생성한다.")
    fun createUser() {
        //given
        val createUserRequest : CreateUser.Request = generateFixture {
            it.set("agreement.termsOfService", true)
            it.set("agreement.privacyPolicy", true)
            it.set("agreement.marketing", true)
            it.set("phoneNumber", PhoneNumber("01012345678"))
            it.maxSize("children", 2)
        }
        println(createUserRequest)
        val createUserResponse : CreateUser.Response = generateFixture()
        given(userService.createUser(createUserRequest)).willReturn(createUserResponse)
        val request = RestDocumentationRequestBuilders.post(UserApi.V1.BASE_URL)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(createUserRequest))
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("registrationToken").description("소셜 로그인 플랫폼에서 발급받은 registrationToken"),
                        fieldWithPath("agreement").description("개인정보 수집 및 이용 동의 여부"),
                        fieldWithPath("agreement.termsOfService").description("서비스 이용약관 동의 여부"),
                        fieldWithPath("agreement.privacyPolicy").description("개인정보 처리방침 동의 여부"),
                        fieldWithPath("agreement.marketing").description("마케팅 정보 수신 동의 여부"),
                        fieldWithPath("phoneNumber.number").description("사용자 전화번호"),
                        fieldWithPath("children").description("사용자의 자녀 정보"),
                        fieldWithPath("children[].name").description("자녀 이름"),
                        fieldWithPath("children[].gender").description("자녀 성별(MALE, FEMALE, NONE)"),
                        fieldWithPath("children[].birth").description("자녀 생년월일"),
                        fieldWithPath("children[].elementSchoolId").description("자녀 초등학교 ID"),
                        fieldWithPath("children[].elementSchoolGrade").description("자녀 초등학교 학년"),
                        fieldWithPath("children[].elementSchoolClassNumber").description("자녀 초등학교 반"),
                        fieldWithPath("children[].allergies").description("자녀 알레르기 정보"),
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("서버 접근을 위한 accessToken"),
                        fieldWithPath("refreshToken").description("서버 접근을 위한 refreshToken")
                    )
                )
            )
    }
}