package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TodoReader(
    private val todoRepository: TodoRepository
) {
    fun findByChildIdUntilDeadline(childId: Long, deadline: LocalDate): List<Todo> {
        return todoRepository.findAllByChildIdAndDeadlineBefore(childId, deadline.plusDays(1))
    }
}