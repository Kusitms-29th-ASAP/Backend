package com.asap.asapbackend.domain.todo.presentation

import com.asap.asapbackend.AbstractRestDocsConfigurer
import com.asap.asapbackend.TOKEN_HEADER_NAME
import com.asap.asapbackend.TOKEN_PREFIX
import com.asap.asapbackend.domain.todo.application.TodoService
import com.asap.asapbackend.domain.todo.application.dto.ChangeTodoStatus
import com.asap.asapbackend.domain.todo.application.dto.CreateTodo
import com.asap.asapbackend.domain.todo.application.dto.GetTodo
import com.asap.asapbackend.domain.todo.domain.vo.Status
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
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.queryParameters
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate


@WebMvcTest(TodoController::class)
class TodoControllerTest : AbstractRestDocsConfigurer() {

    @MockBean
    private lateinit var todoService: TodoService

    @Test
    @DisplayName("할일 추가")
    fun createTodo() {
        //given
        val createTodo: CreateTodo.Request = generateFixture {
            it.setExp(CreateTodo.Request::description, "밋업 잘 마무리하기")
            it.setExp(CreateTodo.Request::todoType, TodoType.HOMEWORK)
            it.setExp(CreateTodo.Request::deadline, LocalDate.parse("2024-05-31"))
        }
        val request = RestDocumentationRequestBuilders.post(TodoApi.V1.BASE_URL)
            .content(objectMapper.writeValueAsString(createTodo))
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
                    requestFields(
                        fieldWithPath("description").description("할 일"),
                        fieldWithPath("todoType").description("유형( NONE,SCHOOL_ANNOUNCEMENT,SUPPLY,HOMEWORK,ETC)"),
                        fieldWithPath("deadline").description("마감일"),
                    )
                )
            )
    }

    @Test
    @DisplayName("할일 전체 불러오기")
    fun getTodo() {
        //given
        val deadline = LocalDate.parse("2024-05-23")
        val getTodo: GetTodo.Response = generateFixture {
            it.setExp(GetTodo.Response::todoList, listOf(
                GetTodo.TodoInfo(1,"체육복 챙기기",TodoType.SUPPLY,LocalDate.parse("2024-05-28"),Status.COMPLETE, true),
                GetTodo.TodoInfo(2,"수학학원 숙제",TodoType.HOMEWORK,LocalDate.parse("2024-05-31"),Status.INCOMPLETE,false)
            ))
        }

        given(todoService.getTodoUntilDeadLine(deadline)).willReturn(getTodo)
        val request = RestDocumentationRequestBuilders.get(TodoApi.V1.BASE_URL)
            .queryParam("deadline", "2024-05-23")
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
                    queryParameters(
                        parameterWithName("deadline").description("마감일")
                    ),
                    responseFields(
                        fieldWithPath("todoList[].todoId").description("todo Id"),
                        fieldWithPath("todoList[].description").description("할 일"),
                        fieldWithPath("todoList[].todoType").description("유형"),
                        fieldWithPath("todoList[].deadline").description("마감일"),
                        fieldWithPath("todoList[].status").description("상태"),
                        fieldWithPath("todoList[].isAssigned").description("할당 여부(선생님:true,학부모:false)")
                    )
                )
            )
    }

    @Test
    @DisplayName("상태 변경(체크표시)")
    fun changeStatus() {
        val todoId = generateFixture<Long>()
        val changeStatusRequest : ChangeTodoStatus.Request = generateFixture{
            it.setExp(ChangeTodoStatus.Request::todoId,todoId)
        }
        //given
        val request = RestDocumentationRequestBuilders.put(TodoApi.V1.BASE_URL)
            .content(objectMapper.writeValueAsString(changeStatusRequest))
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
                    requestFields(
                        fieldWithPath("todoId").description("todo ID")
                    )
                )
            )
    }
}