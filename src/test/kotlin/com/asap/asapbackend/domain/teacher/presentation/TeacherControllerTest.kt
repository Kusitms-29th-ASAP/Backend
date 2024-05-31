package com.asap.asapbackend.domain.teacher.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.teacher.application.TeacherService
import com.asap.asapbackend.domain.teacher.application.dto.CreateTeacher
import com.asap.asapbackend.domain.teacher.application.dto.GetTeacherInfo
import com.asap.asapbackend.domain.teacher.application.dto.LoginTeacher
import com.asap.asapbackend.fixture.generateFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TeacherController::class)
class TeacherControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var teacherService: TeacherService


    @Test
    @DisplayName("선생님 회원가입")
    fun createTeacher() {
        // given
        val teacherCreateRequest: CreateTeacher.Request = generateFixture()
        val request = RestDocumentationRequestBuilders.post(TeacherApi.V1.BASE_URL)
            .content(objectMapper.writeValueAsString(teacherCreateRequest))
            .contentType("application/json")
        // when
        val result = mockMvc.perform(request)
        // then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("username").description("선생님 아이디"),
                        fieldWithPath("password").description("선생님 비밀번호"),
                        fieldWithPath("name").description("선생님 이름"),
                        fieldWithPath("schoolId").description("선생님 학교 id"),
                        fieldWithPath("grade").description("선생님 학년"),
                        fieldWithPath("className").description("선생님 반 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("선생님 로그인")
    fun loginTeacher() {
        // given
        val teacherLoginRequest: LoginTeacher.Request = generateFixture()
        val teacherLoginResponse: LoginTeacher.Response = generateFixture()
        given(teacherService.loginTeacher(teacherLoginRequest)).willReturn(teacherLoginResponse)
        val request = RestDocumentationRequestBuilders.post( TeacherApi.V1.LOGIN)
            .content(objectMapper.writeValueAsString(teacherLoginRequest))
            .contentType("application/json")
        // when
        val result = mockMvc.perform(request)
        // then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("username").description("선생님 아이디"),
                        fieldWithPath("password").description("선생님 비밀번호")
                    ),
                    responseFields(
                        fieldWithPath("accessToken").description("액세스 토큰")
                    )
                )
            )
    }


    @Test
    @DisplayName("선생님 정보 조회")
    fun getTeacherInfo() {
        // given
        val teacherInfoResponse: GetTeacherInfo.Response = generateFixture()
        given(teacherService.getTeacherInfo()).willReturn(teacherInfoResponse)
        val request = RestDocumentationRequestBuilders.get(TeacherApi.V1.BASE_URL)
            .header("Authorization", "Bearer token")
        // when
        val result = mockMvc.perform(request)
        // then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("선생님 access token")
                    ),
                    responseFields(
                        fieldWithPath("schoolName").description("학교 이름"),
                        fieldWithPath("grade").description("학년"),
                        fieldWithPath("className").description("반 이름"),
                        fieldWithPath("teacherName").description("선생님 이름")
                    )
                )
            )
    }
}