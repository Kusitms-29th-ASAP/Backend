package com.asap.asapbackend.domain.todo.domain.repository

import com.asap.asapbackend.domain.todo.domain.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {
}