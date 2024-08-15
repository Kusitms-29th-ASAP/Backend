package com.asap.asapbackend.infrastructure.jpa.menu.repository

import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import com.asap.asapbackend.infrastructure.jpa.menu.entity.TranslatedMenuEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TranslatedMenuJpaRepository : JpaRepository<TranslatedMenuEntity, MultiLanguageId>{

    fun findAllByIdIn(ids: List<MultiLanguageId>): List<TranslatedMenuEntity>


}
