package com.asap.asapbackend.infrastructure.jpa.todo.entity

import com.asap.asapbackend.global.vo.Language
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "translated_todo")
class TranslatedTodoEntity(
    todoId: Long,
    language: Language,
    description: String,
) {

    @EmbeddedId
    val id: MultiLanguageId = MultiLanguageId(todoId, language)

    @Column(
        nullable = false,
        columnDefinition = "text"
    )
    val description: String = description
}