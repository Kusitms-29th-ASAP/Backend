package com.asap.asapbackend.domain.menu.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.domain.menu.application.MenuService
import com.asap.asapbackend.domain.menu.application.dto.GetThisMonthMenu
import com.asap.asapbackend.domain.menu.application.dto.GetTodayMenu
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
import java.time.LocalDate

@WebMvcTest(MenuController::class)
class MenuControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var menuService: MenuService

    @Test
    @DisplayName("오늘의 급식 조회")
    fun getTodayMenu() {
        //given
        val getTodayMenu = GetTodayMenu.Response(
            listOf(
                GetTodayMenu.Meal("유기농쌀밥", false),
                GetTodayMenu.Meal("옹심이미역국", false),
                GetTodayMenu.Meal("잡채무침", false),
                GetTodayMenu.Meal("삼치매실데리구이", false),
                GetTodayMenu.Meal("깍두기", false),
                GetTodayMenu.Meal("우유", true),
                GetTodayMenu.Meal("바나나", false)
            )
        )
        given(menuService.getTodayMenu()).willReturn(getTodayMenu)
        val request = RestDocumentationRequestBuilders.get(MenuApi.V1.TODAY)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accesstoken")
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
                        fieldWithPath("foods[].food").description("음식"),
                        fieldWithPath("foods[].warning").description("알러지 주의 여부")
                    )

                )
            )
    }

    @Test
    @DisplayName("이번달 급식 조회")
    fun getThisMonthMenu(){
        //given
        val getThisMonthMenu = GetThisMonthMenu.Response(
            listOf(
                GetThisMonthMenu.MealDetail(
                    LocalDate.parse("2024-05-02"),
                    listOf(
                        GetThisMonthMenu.Meal("장조림비빔밥", false),
                        GetThisMonthMenu.Meal("베리파이구이", true),
                        GetThisMonthMenu.Meal("김치해장국", false),
                        GetThisMonthMenu.Meal("메추리어묵조림", true),
                        GetThisMonthMenu.Meal("깍두기", false),
                        GetThisMonthMenu.Meal("우유", true),
                        GetThisMonthMenu.Meal("참외", false)
                    )
                ),
                GetThisMonthMenu.MealDetail(
                    LocalDate.parse("2024-05-03"),
                    listOf(
                        GetThisMonthMenu.Meal("(곁들이밥&구이김)", false),
                        GetThisMonthMenu.Meal("토마토스파게티", true),
                        GetThisMonthMenu.Meal("마늘빵구이", true),
                        GetThisMonthMenu.Meal("양송이스프", true),
                        GetThisMonthMenu.Meal("깍두기", false),
                        GetThisMonthMenu.Meal("떠먹는요구르트", true),
                        GetThisMonthMenu.Meal("망고(생)", false)
                    )
                )
            )
        )
        given(menuService.getThisMonthMenu()).willReturn(getThisMonthMenu)
        val request = RestDocumentationRequestBuilders.get(MenuApi.V1.MONTH)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer accesstoken")
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
                        fieldWithPath("menus[].date").description("날짜"),
                        fieldWithPath("menus[].foods[].food").description("음식"),
                        fieldWithPath("menus[].foods[].warning").description("알러지 주의 여부")
                    )
                )
            )
    }
}