package com.asap.asapbackend.domain.todo.presentation

import com.asap.asapbackend.domain.todo.application.TodoService
import com.asap.asapbackend.domain.todo.application.dto.ChangeTodoStatus
import com.asap.asapbackend.domain.todo.application.dto.CreateTodo
import com.asap.asapbackend.domain.todo.application.dto.GetTodo
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class TodoController(
    private val todoService: TodoService
) {
    @PostMapping(TodoApi.V1.BASE_URL)
    fun createTodo(@RequestBody todo: CreateTodo.Request) {
        todoService.createTodo(todo)
    }

    @GetMapping(TodoApi.V1.BASE_URL)
    fun getTodo(@RequestParam deadline: LocalDate): GetTodo.Response {
        return todoService.getTodoUntilDeadLine(deadline)
    }

    @PutMapping(TodoApi.V1.BASE_URL)
    fun changeStatus(@RequestBody request: ChangeTodoStatus.Request){
        todoService.changeStatus(request.todoId)
    }
}