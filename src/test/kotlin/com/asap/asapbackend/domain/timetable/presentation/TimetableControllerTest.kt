package com.asap.asapbackend.domain.timetable.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.timetable.application.TimetableService
import com.asap.asapbackend.domain.timetable.application.dto.GetThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.GetTodayTimetable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TimetableController::class)
class TimetableControllerTest :AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var timetableService: TimetableService

    @Test
    @DisplayName("오늘의 시간표 조회")
    fun getTodayTimetable() {
        //given
        val getTodayTimetable : GetTodayTimetable.Response =
            GetTodayTimetable.Response(
                listOf(
                    GetTodayTimetable.Timetable(1, "국어"),
                    GetTodayTimetable.Timetable(2, "즐거운생활"),
                    GetTodayTimetable.Timetable(3, "자율·자치활동"),
                    GetTodayTimetable.Timetable(4, "수학"),
                    GetTodayTimetable.Timetable(5, "국어")
                )
            )
        given(timetableService.getTodayTimetable()).willReturn(getTodayTimetable)
        val request = RestDocumentationRequestBuilders.get(TimetableApi.V1.TODAY)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer accesstoken")
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
                        fieldWithPath("timetables[].time").description("교시"),
                        fieldWithPath("timetables[].subject").description("과목")
                    )
                )
            )
    }

    @Test
    @DisplayName("일주일 시간표 조회")
    fun getThisWeekTimetable() {
        //given
        val getThisWeekTimetable = GetThisWeekTimetable.Response(
            monday = listOf(
                GetThisWeekTimetable.Period(1, "자율·자치활동"),
                GetThisWeekTimetable.Period(2, "바른생활"),
                GetThisWeekTimetable.Period(3, "슬기로운생활"),
                GetThisWeekTimetable.Period(4, "국어")
            ),
            tuesday = listOf(
                GetThisWeekTimetable.Period(1, "국어"),
                GetThisWeekTimetable.Period(2, "즐거운생활"),
                GetThisWeekTimetable.Period(3, "자율·자치활동"),
                GetThisWeekTimetable.Period(4, "수학"),
                GetThisWeekTimetable.Period(5, "국어")
            ),
            wednesday = listOf(
                GetThisWeekTimetable.Period(1, "자율학습")
            ),
            thursday = listOf(
                GetThisWeekTimetable.Period(1, "수학"),
                GetThisWeekTimetable.Period(2, "국어"),
                GetThisWeekTimetable.Period(3, "바른생활"),
                GetThisWeekTimetable.Period(4, "국어"),
                GetThisWeekTimetable.Period(5, "국어")
            ),
            friday = listOf(
                GetThisWeekTimetable.Period(1, "동아리활동"),
                GetThisWeekTimetable.Period(2, "즐거운생활"),
                GetThisWeekTimetable.Period(3, "수학"),
                GetThisWeekTimetable.Period(4, "국어"),
                GetThisWeekTimetable.Period(5, "즐거운생활"),
                GetThisWeekTimetable.Period(6, "국어")
            )
        )
        given(timetableService.getThisWeekTimetable()).willReturn(getThisWeekTimetable)
        val request = RestDocumentationRequestBuilders.get(TimetableApi.V1.WEEK)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization","Bearer accesstoken")
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
                        fieldWithPath("monday[].time").description("월요일 교시").optional(),
                        fieldWithPath("monday[].subject").description("월요일 과목").optional(),
                        fieldWithPath("tuesday[].time").description("화요일 교시").optional(),
                        fieldWithPath("tuesday[].subject").description("화요일 과목").optional(),
                        fieldWithPath("wednesday[].time").description("수요일 교시").optional(),
                        fieldWithPath("wednesday[].subject").description("수요일 과목").optional(),
                        fieldWithPath("thursday[].time").description("목요일 교시").optional(),
                        fieldWithPath("thursday[].subject").description("목요일 과목").optional(),
                        fieldWithPath("friday[].time").description("금요일 교시").optional(),
                        fieldWithPath("friday[].subject").description("금요일 과목").optional()
                    )
                )
            )
    }
}