package com.asap.asapbackend.domain.classroom.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.TOKEN_HEADER_NAME
import com.asap.asapbackend.TOKEN_PREFIX
import com.asap.asapbackend.domain.classroom.application.ClassroomService
import com.asap.asapbackend.domain.classroom.application.dto.CreateAnnouncement
import com.asap.asapbackend.domain.classroom.application.dto.GetAnnouncements
import com.asap.asapbackend.domain.classroom.application.dto.GetTodayAnnouncement
import com.asap.asapbackend.domain.classroom.domain.vo.AnnouncementDescription
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import com.asap.asapbackend.fixture.generateFixture
import com.navercorp.fixturemonkey.kotlin.setExp
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(ClassroomController::class)
class ClassroomControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var classroomService: ClassroomService

    @Test
    @DisplayName("알림장 추가")
    fun addAnnouncement() {
        // given
        val createAnnouncementRequest: CreateAnnouncement.Request = generateFixture {
            it.setExp(
                CreateAnnouncement.Request::announcementDetails, listOf(
                    CreateAnnouncement.AnnouncementDetails(
                        "독후감 써오기", true, TodoType.HOMEWORK, LocalDate.parse("2024-05-26")
                    ),
                    CreateAnnouncement.AnnouncementDetails(
                        "일기 써오기", false, TodoType.NONE, LocalDate.parse("2024-05-31")
                    )
                )
            )
        }
        val request = RestDocumentationRequestBuilders.post(ClassroomApi.V1.ANNOUNCEMENT)
            .content(objectMapper.writeValueAsString(createAnnouncementRequest))
            .contentType("application/json")
            .header(TOKEN_HEADER_NAME, "$TOKEN_PREFIX teacherToken")
        // when
        val result = mockMvc.perform(request)
        // then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
                        fieldWithPath("writeDate").description("작성일"),
                        fieldWithPath("announcementDetails").description("알림장 내용"),
                        fieldWithPath("announcementDetails[].description").description("알림장 내용"),
                        fieldWithPath("announcementDetails[].isLinkedWithTodo").description("할일과 연결 여부"),
                        fieldWithPath("announcementDetails[].todoType").description("할일 종류(NONE,SCHOOL_ANNOUNCEMENT,SUPPLY,HOMEWORK,ETC)"),
                        fieldWithPath("announcementDetails[].deadline").description("마감일")
                    )
                )
            )
    }

    @Test
    @DisplayName("오늘의 알림장 불러오기")
    fun getTodayAnnouncement() {
        //given
        val getTodayAnnouncement: GetTodayAnnouncement.Response = generateFixture {
            it.setExp(
                GetTodayAnnouncement.Response::descriptions, listOf(
                    AnnouncementDescription("독후감 써오기"),
                    AnnouncementDescription("일기 써오기")
                )
            )
        }

        given(classroomService.getTodayAnnouncement()).willReturn(getTodayAnnouncement)
        val request = RestDocumentationRequestBuilders.get(ClassroomApi.V1.TODAY_ANNOUNCEMENT)
            .contentType(MediaType.APPLICATION_JSON)
            .header(TOKEN_HEADER_NAME, "$TOKEN_PREFIX accessToken")
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    responseFields(
                        fieldWithPath("descriptions[].description").description("알림장 내용")
                    )
                )
            )
    }

    @Test
    @DisplayName("알림장 전체 불러오기")
    fun getAnnouncements() {
        //given
        val getAnnouncements: GetAnnouncements.Response = generateFixture {
            it.setExp(GetAnnouncements.Response::teacher, "윤소민")
            it.setExp(
                GetAnnouncements.Response::announcements, listOf(
                    GetAnnouncements.AnnouncementInfo(
                        listOf(
                            AnnouncementDescription("독후감 써오기"),
                            AnnouncementDescription("일기 써오기")
                        ), LocalDate.parse("2024-05-16")
                    ),
                    GetAnnouncements.AnnouncementInfo(
                        listOf(
                            AnnouncementDescription("수학 익힘책 p5~10 풀기"),
                            AnnouncementDescription("줄넘기 가져오기"),
                            AnnouncementDescription("우유 급식 신청서 회신받아오기")
                        ), LocalDate.parse("2024-05-15")
                    )
                )
            )
        }

        given(classroomService.getAnnouncements()).willReturn(getAnnouncements)
        val request = RestDocumentationRequestBuilders.get(ClassroomApi.V1.ANNOUNCEMENT)
            .contentType(MediaType.APPLICATION_JSON)
            .header(TOKEN_HEADER_NAME, "$TOKEN_PREFIX accessToken")
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    responseFields(
                        fieldWithPath("teacher").description("선생님 성함"),
                        fieldWithPath("announcements[].descriptions[].description").description("알림장 내용"),
                        fieldWithPath("announcements[].writeDate").description("알림장 날짜")
                    )
                )
            )
    }
}