package com.asap.asapbackend.infrastructure.jpa.todo

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.infrastructure.jpa.todo.entity.TodoEntity

object TodoMapper {

    fun toModel(todoEntity: TodoEntity): Todo {
        return Todo(
            id = todoEntity.id,
            type = todoEntity.type,
            description = todoEntity.description,
            childId = todoEntity.childId,
            deadline = todoEntity.deadline,
            isAssigned = todoEntity.isAssigned,
            status = todoEntity.status,
            createdAt = todoEntity.createdAt,
            updatedAt = todoEntity.updatedAt
        )
    }

    fun toEntity(todo: Todo): TodoEntity {
        return TodoEntity(
            id = todo.id,
            type = todo.type,
            description = todo.description,
            childId = todo.childId,
            deadline = todo.deadline,
            isAssigned = todo.isAssigned,
            status = todo.status,
            createdAt = todo.createdAt,
            updatedAt = todo.updatedAt
        )
    }
}