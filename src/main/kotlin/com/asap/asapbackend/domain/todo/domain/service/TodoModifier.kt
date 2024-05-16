package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoModifier(
    private val todoRepository: TodoRepository,
){
    fun changeStatus(todoId: Long) {
        todoRepository.findByIdOrNull(todoId)?.let {
            it.changeStatus()
            todoRepository.save(it)
        }
    }
}