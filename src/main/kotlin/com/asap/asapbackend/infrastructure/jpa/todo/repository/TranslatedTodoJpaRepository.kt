package com.asap.asapbackend.infrastructure.jpa.todo.repository

import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.todo.entity.TranslatedTodoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface TranslatedTodoJpaRepository: JpaRepository<TranslatedTodoEntity, MultiLanguageId> {

    @Query("select t from TranslatedTodoEntity t where t.id in :ids")
    fun findAllByIds(ids: Set<MultiLanguageId>) : Set<TranslatedTodoEntity>

    @Modifying
    @Query("delete from TranslatedTodoEntity t where t.id.id = :todoId")
    fun deleteByTodoId(todoId: Long)
}