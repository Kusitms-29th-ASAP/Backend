package com.asap.asapbackend.domain.child.domain.model

import com.asap.asapbackend.domain.user.domain.model.User
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.*

@Entity
class PrimaryChild(
    user: User,
    child: Child
):BaseDateEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    val user = user

    @ManyToOne(fetch = FetchType.LAZY)
    val child = child
}