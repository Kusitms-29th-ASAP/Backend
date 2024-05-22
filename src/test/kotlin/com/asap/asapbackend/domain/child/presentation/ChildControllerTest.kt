package com.asap.asapbackend.domain.child.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.TOKEN_HEADER_NAME
import com.asap.asapbackend.TOKEN_PREFIX
import com.asap.asapbackend.domain.child.application.ChildService
import com.asap.asapbackend.domain.child.application.dto.ChangeChildInfo
import com.asap.asapbackend.domain.child.application.dto.ChangePrimaryChild
import com.asap.asapbackend.domain.child.application.dto.GetAllChildren
import com.asap.asapbackend.domain.child.application.dto.GetChild
import com.asap.asapbackend.domain.menu.domain.model.Allergy
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
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WebMvcTest(ChildController::class)
class ChildControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var childService: ChildService

    @Test
    @DisplayName("모든 자녀 불러오기")
    fun getAllChildren() {
        //given
        val getAllChildren: GetAllChildren.Response = generateFixture {
            it.setExp(
                GetAllChildren.Response::childList, listOf(
                    GetAllChildren.ChildInfo(true, 5, "유진주", "양원숲초등학교", 2, "3"),
                    GetAllChildren.ChildInfo(false, 6, "박예진", "양원초등학교", 2, "4")
                )
            )
        }
        given(childService.getAllChildren()).willReturn(getAllChildren)
        val request = RestDocumentationRequestBuilders.get(ChildApi.V1.BASE_URL)
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
                        fieldWithPath("childList[].isPrimary").description("선택된 자녀인지"),
                        fieldWithPath("childList[].childId").description("child Id"),
                        fieldWithPath("childList[].childName").description("자녀 이름"),
                        fieldWithPath("childList[].schoolName").description("학교 이름"),
                        fieldWithPath("childList[].grade").description("학년"),
                        fieldWithPath("childList[].classroomName").description("반 이름")
                    )
                )
            )
    }

    @Test
    @DisplayName("단일 자녀 정보 가저오기")
    fun getChild() {
        //given
        val childId = generateFixture<Long>()
        val getChildRequest: GetChild.Request = generateFixture {
            it.setExp(GetChild.Request::childId, childId)
        }
        val getChild: GetChild.Response = generateFixture {
            it.setExp(GetChild.Response::childName, "윤소민")
            it.setExp(GetChild.Response::schoolName, "양원숲초등학교")
            it.setExp(GetChild.Response::grade, 2)
            it.setExp(GetChild.Response::classroomName, "1")
            it.setExp(GetChild.Response::birthday, LocalDate.parse("2003-03-24"))
            it.setExp(GetChild.Response::allergies, listOf(Allergy.EGG, Allergy.PEANUT))
        }
        given(childService.getChild(getChildRequest)).willReturn(getChild)
        val request = RestDocumentationRequestBuilders.get(ChildApi.V1.CHILD, childId.toString())
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
                    pathParameters(
                        parameterWithName("childId").description("자녀 ID")
                    ),
                    responseFields(
                        fieldWithPath("childName").description("자녀 이름"),
                        fieldWithPath("schoolName").description("학교 이름"),
                        fieldWithPath("grade").description("학년"),
                        fieldWithPath("classroomName").description("반 이름"),
                        fieldWithPath("birthday").description("생일"),
                        fieldWithPath("allergies").description("알러지")
                    )
                )
            )
    }

    @Test
    @DisplayName("자녀 정보 변경")
    fun changeChildInfo() {
        //given
        val childId = generateFixture<Long>()
        val changeChildInfo: ChangeChildInfo.Request = generateFixture {
            it.setExp(ChangeChildInfo.Request::childName, "윤소민")
            it.setExp(ChangeChildInfo.Request::birthday, LocalDate.parse("2003-03-24"))
            it.setExp(ChangeChildInfo.Request::allergies, listOf(Allergy.EGG, Allergy.PEANUT))
        }
        val request = RestDocumentationRequestBuilders.get(ChildApi.V1.CHILD, childId.toString())
            .contentType(MediaType.APPLICATION_JSON)
            .header(TOKEN_HEADER_NAME, "$TOKEN_PREFIX accessToken")
            .content(objectMapper.writeValueAsString(changeChildInfo))
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    pathParameters(
                        parameterWithName("childId").description("자녀 ID")
                    ),
                    requestFields(
                        fieldWithPath("childName").description("자녀 이름"),
                        fieldWithPath("birthday").description("생일"),
                        fieldWithPath("allergies").description("알러지")
                    )
                )
            )
    }

    @Test
    @DisplayName("선택한 자녀 변경")
    fun changePrimaryChild() {
        //given
        val childId = generateFixture<Long>()
        val changePrimaryChild = generateFixture{
            it.setExp(ChangePrimaryChild.Request::childId,childId)
        }
        val request = RestDocumentationRequestBuilders.put(ChildApi.V1.PRIMARY_CHILD)
            .contentType(MediaType.APPLICATION_JSON)
            .header(TOKEN_HEADER_NAME, "$TOKEN_PREFIX accessToken")
            .content(objectMapper.writeValueAsString(changePrimaryChild))
        //when
        val result = mockMvc.perform(request)
        //then
        result.andExpect(status().isOk)
            .andDo(
                resultHandler.document(
                    requestHeaders(
                        headerWithName("Authorization").description("Access Token")
                    ),
                    requestFields(
                        fieldWithPath("childId").description("자녀 ID")
                    )
                )
            )
    }
}