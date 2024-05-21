package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoRemover(
    private val todoRepository: TodoRepository
) {
    fun delete(todo: Todo?){
        todo?.let {
            todoRepository.delete(it)
        }
    }
}