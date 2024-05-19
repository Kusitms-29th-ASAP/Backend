package com.asap.asapbackend.domain.announcement.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.announcement.application.SchoolAnnouncementService
import com.asap.asapbackend.domain.announcement.application.dto.GetEducationOfficeAnnouncementDetail
import com.asap.asapbackend.domain.announcement.application.dto.GetEducationOfficeAnnouncementSlice
import com.asap.asapbackend.domain.announcement.application.dto.GetSchoolAnnouncementDetail
import com.asap.asapbackend.domain.announcement.application.dto.GetSchoolAnnouncementSlice
import com.asap.asapbackend.domain.announcement.domain.vo.AnnouncementCategory
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
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(SchoolAnnouncementController::class)
class SchoolAnnouncementControllerTest : AbstractRestDocsConfigurer() {
    @MockBean
    private lateinit var announcementService: SchoolAnnouncementService


    @Test
    @DisplayName("학교 가정통신문 전체 조회")
    fun getSchoolAnnouncement() {
        //given
        val getSchoolAnnouncementSlice =
            GetSchoolAnnouncementSlice.Response(
                hasNext = false,
                schoolAnnouncementInfos = listOf(
                    GetSchoolAnnouncementSlice.SchoolAnnouncementInfo(
                        1,
                        "학교 공지사항 제목",
                        category = AnnouncementCategory.SCHOOL_SCHEDULE,
                        uploadDate = LocalDate.now(),
                        summary = listOf("학교 공지사항 요약", "학교 공지사항 요약", "학교 공지사항 요약"),
                    )
                )
            )
        given(
            announcementService.getSchoolAnnouncementSlice(
                GetSchoolAnnouncementSlice.Request(
                    10,
                    0
                )
            )
        ).willReturn(
            getSchoolAnnouncementSlice
        )
        val request = RestDocumentationRequestBuilders.get(AnnouncementApi.V1.SCHOOL_ANNOUNCEMENT)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accesstoken")
            .param("size", "10")
            .param("page", "0")
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("사용자 Access Token")
                    ),
                    queryParameters(
                        parameterWithName("size").description("페이지 크기"),
                        parameterWithName("page").description("페이지 번호")
                    ),
                    responseFields(
                        fieldWithPath("schoolAnnouncementInfos[].announcementId").description("공지사항 ID"),
                        fieldWithPath("schoolAnnouncementInfos[].title").description("제목"),
                        fieldWithPath("schoolAnnouncementInfos[].category").description("카테고리"),
                        fieldWithPath("schoolAnnouncementInfos[].uploadDate").description("업로드 날짜"),
                        fieldWithPath("schoolAnnouncementInfos[].summary").description("가정통신문 3줄 요약"),
                        fieldWithPath("schoolAnnouncementInfos[].isNew").description("새로운 공지 여부"),
                        fieldWithPath("hasNext").description(
                            "다음 페이지 존재 여부"
                        )
                    )
                )
            )
    }

    @Test
    @DisplayName("학교 가정통신문 상세 조회")
    fun getSchoolAnnouncementDetail() {
        //given
        val getSchoolAnnouncementDetail = GetSchoolAnnouncementDetail.Response(
            title = "학교 공지사항 제목",
            category = AnnouncementCategory.SCHOOL_SCHEDULE,
            uploadDate = LocalDate.now(),
            imageUrls = listOf("https://image.url"),
            highlight = GetSchoolAnnouncementDetail.AnnouncementHighlight(
                keywords = listOf("키워드1", "키워드2"),
                summaries = listOf("요약1", "요약2", "요약3")
            )
        )
        given(announcementService.getSchoolAnnouncementDetail(GetSchoolAnnouncementDetail.Request(1))).willReturn(
            getSchoolAnnouncementDetail
        )
        val request = RestDocumentationRequestBuilders.get(AnnouncementApi.V1.SCHOOL_ANNOUNCEMENT_DETAIL, 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accesstoken")
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("사용자 Access Token")
                    ),
                    pathParameters(
                        parameterWithName("schoolAnnouncementId").description("공지사항 ID")
                    ),
                    responseFields(
                        fieldWithPath("title").description("제목"),
                        fieldWithPath("category").description("카테고리"),
                        fieldWithPath("uploadDate").description("업로드 날짜"),
                        fieldWithPath("imageUrls").description("이미지 URL"),
                        fieldWithPath("highlight.keywords").description("가정통신문 키워드"),
                        fieldWithPath("highlight.summaries").description("가정통신문 3줄 요약")
                    )
                )
            )
    }


    @Test
    @DisplayName("교육청 가정통신문 전체 조회")
    fun getEducationOfficeAnnouncement() {
        //given
        val getEducationOfficeAnnouncementSlice =
            GetEducationOfficeAnnouncementSlice.Response(
                hasNext = false,
                educationOfficeAnnouncementInfos = listOf(
                    GetEducationOfficeAnnouncementSlice.EducationOfficeAnnouncementInfo(
                        1,
                        "교육청 공지사항 제목",
                        category = AnnouncementCategory.EDUCATION_BENEFIT,
                        uploadDate = LocalDate.now(),
                        summary = listOf("교육청 공지사항 요약", "교육청 공지사항 요약", "교육청 공지사항 요약"),
                    )
                )
            )
        given(
            announcementService.getEducationOfficeAnnouncementSlice(
                GetEducationOfficeAnnouncementSlice.Request(
                    10,
                    0
                )
            )
        ).willReturn(
            getEducationOfficeAnnouncementSlice
        )
        val request = RestDocumentationRequestBuilders.get(AnnouncementApi.V1.EDUCATION_OFFICE_ANNOUNCEMENT)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accesstoken")
            .param("size", "10")
            .param("page", "0")
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("사용자 Access Token")
                    ),
                    queryParameters(
                        parameterWithName("size").description("페이지 크기"),
                        parameterWithName("page").description("페이지 번호")
                    ),
                    responseFields(
                        fieldWithPath("educationOfficeAnnouncementInfos[].announcementId").description("공지사항 ID"),
                        fieldWithPath("educationOfficeAnnouncementInfos[].title").description("제목"),
                        fieldWithPath("educationOfficeAnnouncementInfos[].category").description("카테고리"),
                        fieldWithPath("educationOfficeAnnouncementInfos[].uploadDate").description("업로드 날짜"),
                        fieldWithPath("educationOfficeAnnouncementInfos[].summary").description("교육청 공지사항 3줄 요약"),
                        fieldWithPath("educationOfficeAnnouncementInfos[].isNew").description("새로운 공지 여부"),
                        fieldWithPath("hasNext").description(
                            "다음 페이지 존재 여부"
                        )
                    )
                )
            )
    }

    @Test
    @DisplayName("교육청 가정통신문 상세 조회")
    fun getEducationOfficeAnnouncementDetail() {
        //given
        val getEducationOfficeAnnouncementDetail = GetEducationOfficeAnnouncementDetail.Response(
            title = "교육청 공지사항 제목",
            category = AnnouncementCategory.EDUCATION_BENEFIT,
            uploadDate = LocalDate.now(),
            imageUrls = listOf("https://image.url"),
            highlight = GetEducationOfficeAnnouncementDetail.AnnouncementHighlight(
                keywords = listOf("키워드1", "키워드2"),
                summaries = listOf("요약1", "요약2", "요약3")
            )
        )
        given(announcementService.getEducationOfficeAnnouncementDetail(GetEducationOfficeAnnouncementDetail.Request(1))).willReturn(
            getEducationOfficeAnnouncementDetail
        )
        val request = RestDocumentationRequestBuilders.get(AnnouncementApi.V1.EDUCATION_OFFICE_ANNOUNCEMENT_DETAIL, 1)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accesstoken")
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("사용자 Access Token")
                    ),
                    pathParameters(
                        parameterWithName("educationOfficeAnnouncementId").description("공지사항 ID")
                    ),
                    responseFields(
                        fieldWithPath("title").description("제목"),
                        fieldWithPath("category").description("카테고리"),
                        fieldWithPath("uploadDate").description("업로드 날짜"),
                        fieldWithPath("imageUrls").description("이미지 URL"),
                        fieldWithPath("highlight.keywords").description("가정통신문 키워드"),
                        fieldWithPath("highlight.summaries").description("가정통신문 3줄 요약")
                    )
                )
            )
    }
}