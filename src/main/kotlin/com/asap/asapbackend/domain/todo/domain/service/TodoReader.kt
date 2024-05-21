package com.asap.asapbackend.domain.todo.domain.service

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TodoReader(
    private val todoRepository: TodoRepository
) {
    fun findTodoDueAfterDayByChildId(childId: Long, deadline: LocalDate): List<Todo> {
        return todoRepository.findAllByChildIdAndDeadlineAfter(childId, deadline.minusDays(1))
    }

    fun findAllByChildId(todoId: Long) : List<Todo> {
        return todoRepository.findAllByChildId(todoId)
    }

    fun findByIdOrNull(todoId: Long) : Todo? {
        return todoRepository.findByIdOrNull(todoId)
    }
}