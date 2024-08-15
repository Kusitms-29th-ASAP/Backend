package com.asap.asapbackend.infrastructure.jpa.menu.entity

import com.asap.asapbackend.global.vo.Language
import com.asap.asapbackend.infrastructure.jpa.MultiLanguageId
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "translated_menu")
class TranslatedMenuEntity(
    menuId: Long,
    language: Language,
    foods: List<FoodEntity>
) {
    @Id
    val id: MultiLanguageId = MultiLanguageId(menuId, language)

    @JdbcTypeCode(value = SqlTypes.JSON)
    val foods: List<FoodEntity> = foods
}