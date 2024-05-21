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
    fun findTodoDueAfterDayByChildId(childId: Long, date: LocalDate): List<Todo> {
        return todoRepository.findAllByChildIdAndDeadlineAfter(childId, date.minusDays(1))
    }

    fun findByIdOrNull(todoId: Long) : Todo? {
        return todoRepository.findByIdOrNull(todoId)
    }

    fun findAllByUserId(userId: Long) : List<Todo> {
        return todoRepository.findAllByUserId(userId)
    }
}