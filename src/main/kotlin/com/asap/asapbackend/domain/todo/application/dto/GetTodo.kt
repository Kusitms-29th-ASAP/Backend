package com.asap.asapbackend.domain.todo.application.dto

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.vo.Status
import com.asap.asapbackend.domain.todo.domain.vo.TodoType
import java.time.LocalDate

class GetTodo {
    data class Response(
        val todoList: List<TodoInfo>
    )

    data class TodoInfo(
        val todoId: Long,
        val description: String,
        val todoType: TodoType,
        val deadline: LocalDate,
        val status: Status
    )

    fun toTodoInfo(todos: List<Todo>): List<TodoInfo> {
        return todos.map {
            TodoInfo(
                todoId = it.id,
                description = it.description,
                todoType = it.type,
                deadline = it.deadline,
                status = it.status
            )
        }
    }
}