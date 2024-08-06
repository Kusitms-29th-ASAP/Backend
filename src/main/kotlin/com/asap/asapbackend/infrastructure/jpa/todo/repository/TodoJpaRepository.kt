package com.asap.asapbackend.infrastructure.jpa.todo.repository

import com.asap.asapbackend.infrastructure.jpa.todo.entity.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface TodoJpaRepository : JpaRepository<TodoEntity, Long> {

    fun findAllByChildIdAndDeadlineAfter(childId: Long, date: LocalDate): List<TodoEntity>

    @Query("""
        select t
        from TodoEntity t
        join Child c on c.id = t.childId and c.parent.id = :userId
        where t.id = :todoId
    """)
    fun findByUserIdAndTodoId(userId: Long, todoId: Long): TodoEntity?
}