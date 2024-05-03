package com.asap.asapbackend.domain.user.domain.model

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class SocialInfo(
    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    val socialId: String,
    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(100)"
    )
    val provider: Provider
) {
}