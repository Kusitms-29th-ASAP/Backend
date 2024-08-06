package com.asap.asapbackend.infrastructure.jpa

import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

open class BaseEntity(
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
}