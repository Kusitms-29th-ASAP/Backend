package com.asap.asapbackend.domain.user.domain.model

import com.asap.asapbackend.domain.user.domain.enum.Provider
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class User(
    socialId: String,
    provider: Provider,
) : BaseDateEntity() {

    @Column(
        nullable = false,
        columnDefinition = "varchar(255)"
    )
    val socialId: String = socialId

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        columnDefinition = "varchar(100)"
    )
    val provider: Provider = provider

}