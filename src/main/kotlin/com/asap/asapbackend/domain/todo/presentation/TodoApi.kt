package com.asap.asapbackend.domain.todo.presentation

object TodoApi {
    object V1{
        const val BASE_URL = "/api/v1/todos"
        const val TODO_DETAIL = "$BASE_URL/{todoId}"
    }
}