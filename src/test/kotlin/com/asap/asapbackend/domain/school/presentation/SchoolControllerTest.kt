package com.asap.asapbackend.domain.school.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.school.application.SchoolService
import com.asap.asapbackend.domain.school.application.dto.GetSchools
import com.asap.asapbackend.fixture.generateFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(SchoolController::class)
class SchoolControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var schoolService: SchoolService

    @Test
    @DisplayName("키워드로 학교 조회")
    fun getSchool() {
        //given
        val keyword = "구로"
        val getSchoolsResponse = GetSchools.Response(
            schools = listOf(
                GetSchools.School(457, "서울신구로초등학교", "서울특별시 구로구 가마산로27길 69"),
                GetSchools.School(469, "서울동구로초등학교", "서울특별시 구로구 구로중앙로14길 43"),
                GetSchools.School(485, "서울구로초등학교", "서울특별시 구로구 구로중앙로27나길 9"),
                GetSchools.School(486, "서울구로남초등학교", "서울특별시 구로구 디지털로27길 76")
            )
        )
        given(schoolService.getSchools(keyword)).willReturn(getSchoolsResponse)
        val request = RestDocumentationRequestBuilders.get(SchoolApi.V1.BASE_URL)
            .param("keyword", keyword)
            .contentType(MediaType.APPLICATION_JSON)
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    queryParameters(
                        parameterWithName("keyword").description("학교를 검색할 키워드")
                    ),
                    responseFields(
                        fieldWithPath("schools[].id").description("학교 아이디").optional(),
                        fieldWithPath("schools[].name").description("학교 이름").optional(),
                        fieldWithPath("schools[].address").description("학교 주소").optional()
                    )
                )
            )
    }
}
