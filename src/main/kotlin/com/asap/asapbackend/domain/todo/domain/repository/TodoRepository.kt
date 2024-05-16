package com.asap.asapbackend.domain.todo.domain.repository

import com.asap.asapbackend.domain.todo.domain.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface TodoRepository : JpaRepository<Todo, Long> {
    fun findAllByChildIdAndDeadlineBefore(childId: Long, deadline: LocalDate): List<Todo>
}