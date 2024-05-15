package com.asap.asapbackend.domain.todo.application

import com.asap.asapbackend.domain.child.domain.service.ChildReader
import com.asap.asapbackend.domain.todo.application.dto.CreateTodo
import com.asap.asapbackend.domain.todo.application.dto.GetTodo
import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.domain.service.TodoAppender
import com.asap.asapbackend.domain.todo.domain.service.TodoModifier
import com.asap.asapbackend.domain.todo.domain.service.TodoReader
import com.asap.asapbackend.global.security.getCurrentUserId
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TodoService(
    private val todoAppender: TodoAppender,
    private val childReader: ChildReader,
    private val todoReader: TodoReader,
    private val todoModifier: TodoModifier
) {
    fun createTodo(request: CreateTodo.Request) {
        val userId = getCurrentUserId()
        val child = childReader.findPrimaryChild(userId)
        val todo = Todo(
            type = request.todoType,
            description = request.description,
            child = child,
            deadline = request.deadline
        )
        todoAppender.appendTodo(todo)
    }

    fun getTodoUntilDeadLine(deadline: LocalDate): GetTodo.Response {
        val userId = getCurrentUserId()
        val childId = childReader.findPrimaryChild(userId).id
        val todoDataList = todoReader.findByChildIdUntilDeadline(childId, deadline)
        val todoList = todoDataList.map {
            GetTodo.Todo(
                it.id, it.description, it.type, it.deadline, it.status
            )
        }
        return GetTodo.Response(todoList)
    }

    fun changeStatus(todoId: Long) {
        todoModifier.changeStatus(todoId)
    }
}