package com.asap.asapbackend.domain.classroom.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.TOKEN_HEADER_NAME
import com.asap.asapbackend.TOKEN_PREFIX
import com.asap.asapbackend.domain.classroom.application.ClassroomService
import com.asap.asapbackend.domain.classroom.application.dto.CreateClassroomAnnouncement
import com.asap.asapbackend.domain.classroom.application.dto.GetClassroomAnnouncementDetail
import com.asap.asapbackend.domain.classroom.application.dto.GetClassroomAnnouncements
import com.asap.asapbackend.domain.classroom.application.dto.GetTodayClassroomAnnouncement
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
    fun addClassroomAnnouncement() {
        // given
        val createClassroomAnnouncementRequest: CreateClassroomAnnouncement.Request = generateFixture {
            it.setExp(
                CreateClassroomAnnouncement.Request::announcementDetails, listOf(
                    CreateClassroomAnnouncement.AnnouncementDetails(
                        "독후감 써오기", true, TodoType.HOMEWORK, LocalDate.parse("2024-05-26")
                    ),
                    CreateClassroomAnnouncement.AnnouncementDetails(
                        "일기 써오기", false, TodoType.NONE, LocalDate.parse("2024-05-31")
                    )
                )
            )
        }
        val request = RestDocumentationRequestBuilders.post(ClassroomApi.V1.ANNOUNCEMENT)
            .content(objectMapper.writeValueAsString(createClassroomAnnouncementRequest))
            .contentType("application/json")
            .header(TOKEN_HEADER_NAME, "$TOKEN_PREFIX teacherToken")
        // when
        val result = mockMvc.perform(request)
        // then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestFields(
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
    fun getTodayClassroomAnnouncement() {
        //given
        val getTodayClassroomAnnouncement: GetTodayClassroomAnnouncement.Response = generateFixture {
            it.setExp(
                GetTodayClassroomAnnouncement.Response::descriptions, listOf(
                    AnnouncementDescription("독후감 써오기"),
                    AnnouncementDescription("일기 써오기"),
                    AnnouncementDescription("줄넘기 가져오기")
                )
            )
        }

        given(classroomService.getTodayClassroomAnnouncement()).willReturn(getTodayClassroomAnnouncement)
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
                        fieldWithPath("announcementId").description("알림장 id"),
                        fieldWithPath("descriptions[].description").description("알림장 내용")
                    )
                )
            )
    }

    @Test
    @DisplayName("알림장 전체 불러오기")
    fun getClassroomAnnouncements() {
        //given
        val getClassroomAnnouncements: GetClassroomAnnouncements.Response = generateFixture {
            it.setExp(GetClassroomAnnouncements.Response::teacherName, "윤소민")
            it.setExp(
                GetClassroomAnnouncements.Response::announcements, listOf(
                    GetClassroomAnnouncements.AnnouncementInfo(
                        listOf(
                            AnnouncementDescription("독후감 써오기"),
                            AnnouncementDescription("일기 써오기")
                        ), LocalDate.parse("2024-05-16")
                    ),
                    GetClassroomAnnouncements.AnnouncementInfo(
                        listOf(
                            AnnouncementDescription("수학 익힘책 p5~10 풀기"),
                            AnnouncementDescription("줄넘기 가져오기"),
                            AnnouncementDescription("우유 급식 신청서 회신받아오기")
                        ), LocalDate.parse("2024-05-15")
                    )
                )
            )
        }

        given(classroomService.getClassroomAnnouncements()).willReturn(getClassroomAnnouncements)
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
                        fieldWithPath("teacherName").description("선생님 성함"),
                        fieldWithPath("announcements[].descriptions[].description").description("알림장 내용"),
                        fieldWithPath("announcements[].writeDate").description("알림장 날짜")
                    )
                )
            )
    }

    @Test
    @DisplayName("알림장 상세 불러오기")
    fun getClassroomAnnouncementDetail() {
        val classroomAnnouncementId = generateFixture<Long>()
        //given
        val getClassroomAnnouncementDetail: GetClassroomAnnouncementDetail.Response = generateFixture {
            it.setExp(GetClassroomAnnouncementDetail.Response::teacherName, "윤소민")
            it.setExp(GetClassroomAnnouncementDetail.Response::writeDate, LocalDate.parse("2024-05-20"))
            it.setExp(
                GetClassroomAnnouncementDetail.Response::descriptions, listOf(
                    AnnouncementDescription("독후감 써오기"),
                    AnnouncementDescription("일기 써오기"),
                    AnnouncementDescription("줄넘기 가져오기")
                )
            )
        }

        given(classroomService.getClassroomAnnouncementDetail(classroomAnnouncementId))
            .willReturn(getClassroomAnnouncementDetail)
        val request = RestDocumentationRequestBuilders.get(ClassroomApi.V1.ANNOUNCEMENT+"/{classroomAnnouncementId}",classroomAnnouncementId.toString())
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
                        fieldWithPath("teacherName").description("선생님 성함"),
                        fieldWithPath("writeDate").description("알림장 날짜"),
                        fieldWithPath("descriptions[].description").description("알림장 내용")
                    )
                )
            )
    }
}