package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import com.asap.asapbackend.domain.todo.event.MultiTodoCreateEvent
import com.asap.asapbackend.domain.todo.event.TodoCreateEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class TodoAppender(
    private val todoRepository: TodoRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun appendAllBatch(todos: Set<Todo>) {
        todoRepository.insertBatch(todos)
        applicationEventPublisher.publishEvent(MultiTodoCreateEvent(todos))
    }

    fun appendTodo(todo: Todo): Todo {
        val createdTodo = todoRepository.save(todo)
        applicationEventPublisher.publishEvent(TodoCreateEvent(createdTodo))
        return createdTodo
    }
}