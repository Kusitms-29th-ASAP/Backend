package com.asap.asapbackend.domain.classroom.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.TOKEN_HEADER_NAME
import com.asap.asapbackend.TOKEN_PREFIX
import com.asap.asapbackend.domain.classroom.application.ClassroomService
import com.asap.asapbackend.domain.classroom.application.dto.CreateAnnouncement
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import com.asap.asapbackend.fixture.generateFixture
import com.navercorp.fixturemonkey.kotlin.setExp
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ClassroomController::class)
class ClassroomControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var classroomService: ClassroomService


    @Test
    @DisplayName("알림장 추가")
    fun addAnnouncement() {
        // given
        val createAnnouncementRequest: CreateAnnouncement.Request = generateFixture {
            it.setExp(CreateAnnouncement.Request::announcementDetails, listOf(
                generateFixture {
                    it.setExp(CreateAnnouncement.AnnouncementDetails::description, "안녕하세요")
                    it.setExp(CreateAnnouncement.AnnouncementDetails::isLinkedWithTodo, true)
                    it.setExp(CreateAnnouncement.AnnouncementDetails::todoType, TodoType.SCHOOL_ANNOUNCEMENT)
                }
            ))
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


}