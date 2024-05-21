package com.asap.asapbackend.domain.todo.application.dto

class DeleteTodo {
    data class Request (
        val todoId: Long
    )
}