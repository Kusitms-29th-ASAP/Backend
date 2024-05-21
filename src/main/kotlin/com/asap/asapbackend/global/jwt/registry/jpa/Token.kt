package com.asap.asapbackend.global.jwt.registry.jpa

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_token_token", columnList = "token")
    ]
)
class Token(
    userId: Long,
    token: String,
) : BaseDateEntity() {

    val userId: Long = userId

    val token: String = token
}