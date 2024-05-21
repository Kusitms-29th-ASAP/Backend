package com.asap.asapbackend.domain.todo.application.dto

class ChangeTodoStatus {
    data class Request(
        val todoId: Long
    )
}