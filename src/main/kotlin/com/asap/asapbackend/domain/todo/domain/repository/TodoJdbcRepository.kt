package com.asap.asapbackend.domain.todo.domain.repository

import com.asap.asapbackend.domain.todo.domain.model.Todo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement

@Repository
class TodoJdbcRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun insertBatch(todos: Set<Todo>) {
        val sql = """
            insert into todo (created_at, updated_at, deadline, description, status, type, child_id)
            values (now(), now(), ?, ?, ?, ?, ?)
        """.trimIndent()
        jdbcTemplate.batchUpdate(sql, todos, todos.size){ ps : PreparedStatement, todo: Todo ->
            ps.setObject(1, todo.deadline)
            ps.setObject(2, todo.description)
            ps.setObject(3, todo.status.name)
            ps.setObject(4, todo.type.name)
            ps.setLong(5, todo.child.id)
        }
    }
}