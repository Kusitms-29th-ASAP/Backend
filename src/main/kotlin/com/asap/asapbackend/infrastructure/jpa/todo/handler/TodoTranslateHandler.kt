package com.asap.asapbackend.infrastructure.jpa.todo.handler

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
        val translatedDescription = textTranslator.translate(event.todo.description)
        translatedDescription.forEach { (language, text) ->
            translatedTodoJpaRepository.save(
                TranslatedTodoEntity(
                    id = event.todo.id,
                    language = language,
                    description = text
                )
            )
        }
    }


    @ApplicationModuleListener
    fun handle(event: MultiTodoCreateEvent) {
        println("TodoCreateEvent: $event")
    }
}