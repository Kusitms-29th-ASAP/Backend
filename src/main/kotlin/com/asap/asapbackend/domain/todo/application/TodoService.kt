package com.asap.asapbackend.domain.todo.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.todo.application.dto.CreateTodo
import com.asap.asapbackend.domain.todo.application.dto.GetTodo
import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.service.TodoAppender
import com.asap.asapbackend.domain.todo.domain.service.TodoModifier
import com.asap.asapbackend.domain.todo.domain.service.TodoReader
import com.asap.asapbackend.domain.todo.domain.service.TodoRemover
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class TodoService(
    private val todoAppender: TodoAppender,
    private val childReader: ChildReader,
    private val todoReader: TodoReader,
    private val todoModifier: TodoModifier,
    private val todoRemover: TodoRemover
) {
    @Transactional
    fun createTodo(request: CreateTodo.Request) {
        val userId = getCurrentUserId()
        val child = childReader.findPrimaryChild(userId)
        val todo = Todo(
            type = request.todoType,
            description = request.description,
            child = child,
            deadline = request.deadline,
            isAssigned = false
        )
        todoAppender.appendTodo(todo)
    }

    fun getTodoDueAfterDate(date: LocalDate): GetTodo.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val todoDataList = todoReader.findTodoDueAfterDayByChildId(childId, date)
        val todoList = GetTodo().toTodoInfo(todoDataList)
        return GetTodo.Response(todoList)
    }

    @Transactional
    fun changeStatus(todoId: Long) {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val todo = todoReader.findByIdOrNull(todoId)
        if (todoReader.findAllByChildId(childId).contains(todo)) {
            todoModifier.changeTodoStatus(todo)
        }
    }

    @Transactional
    fun deleteTodo(todoId: Long) {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        todoReader.findByIdOrNull(todoId)?.let { todo ->
            if (todoReader.findAllByChildId(childId).contains(todo) && !todo.isAssigned) {
                todoRemover.delete(todo)
            }
        }
    }
}