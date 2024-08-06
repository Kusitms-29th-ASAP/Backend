package com.asap.asapbackend.infrastructure.jpa

import com.asap.asapbackend.global.vo.Language
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
class MultiLanguageId(
    id: Long,
    language: Language
) {
    val id: Long = id

    @Enumerated(value = EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(50)"
    )
    val language: Language = language
}