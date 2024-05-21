package com.asap.asapbackend.domain.todo.domain.repository

import com.asap.asapbackend.domain.todo.domain.model.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TodoRepository : JpaRepository<Todo, Long> {
    fun findAllByChildIdAndDeadlineAfter(childId: Long, deadline: LocalDate): List<Todo>

    @Query("""
        select t
        from Todo t
        join fetch t.child
        join PrimaryChild p on p.childId = t.child.id and p.userId = :userId
    """)
    fun findAllByUserId(userId: Long): List<Todo>
}