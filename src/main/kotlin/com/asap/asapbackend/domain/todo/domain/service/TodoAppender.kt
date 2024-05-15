package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoJdbcRepository
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Service

@Service
class TodoAppender(
    private val todoRepository: TodoRepository,
    private val todoJdbcRepository: TodoJdbcRepository
) {

    fun appendAll(todos: Set<Todo>) {
        todoRepository.saveAll(todos)
    }

    fun appendAllBatch(todos: Set<Todo>) {
        todoJdbcRepository.insertBatch(todos)
    }

    fun appendTodo(todo: Todo): Todo {
        return todoRepository.save(todo)
    }
}