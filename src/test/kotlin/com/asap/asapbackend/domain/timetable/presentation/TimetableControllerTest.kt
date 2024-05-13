package com.asap.asapbackend.domain.timetable.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.timetable.application.TimetableService
import com.asap.asapbackend.domain.timetable.application.dto.ReadThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.ReadTodayTimetable
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
        val getTodayTimetable : ReadTodayTimetable.Response =
            ReadTodayTimetable.Response(
                listOf(
                    ReadTodayTimetable.Timetable(1, "국어"),
                    ReadTodayTimetable.Timetable(2, "즐거운생활"),
                    ReadTodayTimetable.Timetable(3, "자율·자치활동"),
                    ReadTodayTimetable.Timetable(4, "수학"),
                    ReadTodayTimetable.Timetable(5, "국어")
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
        val getThisWeekTimetable = ReadThisWeekTimetable.Response(
            monday = listOf(
                ReadThisWeekTimetable.Period(1, "자율·자치활동"),
                ReadThisWeekTimetable.Period(2, "바른생활"),
                ReadThisWeekTimetable.Period(3, "슬기로운생활"),
                ReadThisWeekTimetable.Period(4, "국어")
            ),
            tuesday = listOf(
                ReadThisWeekTimetable.Period(1, "국어"),
                ReadThisWeekTimetable.Period(2, "즐거운생활"),
                ReadThisWeekTimetable.Period(3, "자율·자치활동"),
                ReadThisWeekTimetable.Period(4, "수학"),
                ReadThisWeekTimetable.Period(5, "국어")
            ),
            wednesday = listOf(
                ReadThisWeekTimetable.Period(1, "자율학습") // 여기에 원하는 요일의 항목을 추가할 수 있습니다.
            ),
            thursday = listOf(
                ReadThisWeekTimetable.Period(1, "수학"),
                ReadThisWeekTimetable.Period(2, "국어"),
                ReadThisWeekTimetable.Period(3, "바른생활"),
                ReadThisWeekTimetable.Period(4, "국어"),
                ReadThisWeekTimetable.Period(5, "국어")
            ),
            friday = listOf(
                ReadThisWeekTimetable.Period(1, "동아리활동"),
                ReadThisWeekTimetable.Period(2, "즐거운생활"),
                ReadThisWeekTimetable.Period(3, "수학"),
                ReadThisWeekTimetable.Period(4, "국어"),
                ReadThisWeekTimetable.Period(5, "즐거운생활"),
                ReadThisWeekTimetable.Period(6, "국어")
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