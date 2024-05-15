package com.asap.asapbackend.domain.todo.application.dto

import com.asap.asapbackend.domain.todo.domain.vo.Status
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import java.time.LocalDate

class GetTodo {
    data class Response(
        val todoList : List<Todo>
    )
    data class Todo(
        val todoId : Long,
        val description: String,
        val todoType: TodoType,
        val deadline: LocalDate,
        val status : Status
    )
}