package com.asap.asapbackend.domain.todo.event

import com.asap.asapbackend.domain.todo.domain.model.Todo

data class TodoCreateEvent(
    val todo: Todo
) {
}

data class MultiTodoCreateEvent(
    val todos: Set<Todo>
) {
}