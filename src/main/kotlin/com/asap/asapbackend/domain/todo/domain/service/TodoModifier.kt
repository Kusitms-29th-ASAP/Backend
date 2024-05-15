package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.exception.TodoException
import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoModifier(
    private val todoRepository: TodoRepository,
){
    fun changeStatus(todoId: Long){
        val todo = findTodo {
            todoRepository.findByIdOrNull(todoId)
        }
        todo.changeStatus()
        todoRepository.save(todo)
    }
    private fun findTodo(function: () -> Todo?): Todo {
        return function() ?: throw TodoException.TodoNotFoundException()
    }
}