package com.asap.asapbackend.domain.todo.domain.repository

import com.asap.asapbackend.domain.todo.domain.model.Todo
import java.time.LocalDate

interface TodoRepository {
    fun findAllByChildIdAndDeadlineAfter(childId: Long, deadline: LocalDate): List<Todo>

    fun findByUserIdAndTodoId(userId: Long, todoId: Long): Todo?

    fun save(todo: Todo): Todo

    fun delete(todo: Todo)

    fun saveAll(todos: List<Todo>): List<Todo>
}