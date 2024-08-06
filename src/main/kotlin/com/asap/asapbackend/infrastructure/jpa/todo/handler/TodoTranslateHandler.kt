package com.asap.asapbackend.infrastructure.jpa.todo.handler

import com.asap.asapbackend.domain.todo.domain.model.Todo
import com.asap.asapbackend.domain.todo.event.MultiTodoCreateEvent
import com.asap.asapbackend.domain.todo.event.TodoCreateEvent
import com.asap.asapbackend.global.util.TextTranslator
import com.asap.asapbackend.infrastructure.jpa.todo.entity.TranslatedTodoEntity
import com.asap.asapbackend.infrastructure.jpa.todo.repository.TranslatedTodoJpaRepository
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component


@Component
class TodoTranslateHandler(
    private val textTranslator: TextTranslator,
    private val translatedTodoJpaRepository: TranslatedTodoJpaRepository
) {

    @ApplicationModuleListener
    fun handle(event: TodoCreateEvent) {
        translateTodo(event.todo)
    }


    @ApplicationModuleListener
    fun handle(event: MultiTodoCreateEvent) {
        event.todos.forEach {
            translateTodo(it)
        }
    }

    private fun translateTodo(todo: Todo) {
        val translatedDescription = textTranslator.translate(todo.description)
        translatedDescription.forEach { (language, text) ->
            translatedTodoJpaRepository.save(
                TranslatedTodoEntity(
                    id = todo.id,
                    language = language,
                    description = text
                )
            )
        }
    }
}