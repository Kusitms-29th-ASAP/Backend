package com.asap.asapbackend.domain.todo.application.dto

import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import java.time.LocalDate

class CreateTodo {
    data class Request(
        val description: String,
        val todoType: TodoType,
        val deadline: LocalDate
    )
}