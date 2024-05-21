package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoModifier(
    private val todoRepository: TodoRepository,
){
    fun changeTodoStatus(todo: Todo) {
        todo.changeStatus()
        todoRepository.save(todo)
    }

    fun changeTodoStatusBtUserIdAndTodoId(userId: Long, todoId: Long){
        todoRepository.findByUserIdAndTodoId(userId, todoId)?.let {
            changeTodoStatus(it)
            todoRepository.save(it)
        }
    }
}