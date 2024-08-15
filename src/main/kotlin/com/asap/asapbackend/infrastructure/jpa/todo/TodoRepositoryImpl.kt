package com.asap.asapbackend.infrastructure.jpa.todo

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.repository.TodoRepository
import com.asap.asapbackend.global.util.LanguageExtractor
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.todo.entity.TodoEntity
import com.asap.asapbackend.infrastructure.jpa.todo.entity.TranslatedTodoEntity
import com.asap.asapbackend.infrastructure.jpa.todo.repository.TodoJpaRepository
import com.asap.asapbackend.infrastructure.jpa.todo.repository.TranslatedTodoJpaRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.PreparedStatement
import java.time.LocalDate

@Repository
class TodoRepositoryImpl(
    private val todoJpaRepository: TodoJpaRepository,
    private val translatedTodoJpaRepository: TranslatedTodoJpaRepository,
    private val languageExtractor: LanguageExtractor,
    private val jdbcTemplate: JdbcTemplate,
) : TodoRepository {

    override fun findAllByChildIdAndDeadlineAfter(childId: Long, deadline: LocalDate): List<Todo> {
        val todos = todoJpaRepository.findAllByChildIdAndDeadlineAfter(childId, deadline)
        val todoDescriptions =
            translatedTodoJpaRepository.findAllByIds(getTodosMultiLanguageIds(todos))
                .associate { it.id.id to it.description }
        return todos.map {
            it.changeDescription(todoDescriptions[it.id])
            TodoMapper.toModel(it)
        }
    }


    override fun findByUserIdAndTodoId(userId: Long, todoId: Long): Todo? {
        val todo = todoJpaRepository.findByUserIdAndTodoId(userId, todoId) ?: return null
        val targetLanguageTodoId = MultiLanguageId(todo.id, languageExtractor.getRequestLanguage())
        translatedTodoJpaRepository.findByIdOrNull(targetLanguageTodoId)?.let {
            todo.changeDescription(it.description)
        }
        return TodoMapper.toModel(todo)
    }

    override fun save(todo: Todo): Todo {
        val savedTodo = todoJpaRepository.save(TodoMapper.toEntity(todo))

        return TodoMapper.toModel(savedTodo)
    }

    override fun insertBatch(todos: Set<Todo>) {
        val sql = """
            insert into todo (created_at, updated_at, deadline, description, status, type, child_id,is_assigned)
            values (now(), now(), ?, ?, ?, ?, ?,?)
        """.trimIndent()
        jdbcTemplate.batchUpdate(sql, todos, todos.size) { ps: PreparedStatement, todo: Todo ->
            ps.setObject(1, todo.deadline)
            ps.setObject(2, todo.description)
            ps.setObject(3, todo.status.name)
            ps.setObject(4, todo.type.name)
            ps.setLong(5, todo.childId)
            ps.setObject(6, todo.isAssigned)
        }
    }

    override fun delete(todo: Todo) {
        todoJpaRepository.delete(TodoMapper.toEntity(todo))
        translatedTodoJpaRepository.deleteByTodoId(todo.id)
    }

    private fun getTodosMultiLanguageIds(todos: List<TodoEntity>): Set<MultiLanguageId> {
        val targetLanguageTodoIds =
            todos.map { MultiLanguageId(it.id, languageExtractor.getRequestLanguage()) }.toSet()
        return targetLanguageTodoIds
    }
}