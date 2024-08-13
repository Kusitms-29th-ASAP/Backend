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


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MultiLanguageId

        if (id != other.id) return false
        if (language != other.language) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + language.hashCode()
        return result
    }


}