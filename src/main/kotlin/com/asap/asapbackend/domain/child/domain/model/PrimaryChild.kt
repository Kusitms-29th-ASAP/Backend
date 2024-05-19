package com.asap.asapbackend.domain.child.domain.model

import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_primary_child_user_id", columnList = "userId"),
    ]
)
class PrimaryChild(
    userId: Long,
    childId: Long
):BaseDateEntity() {

    @Column(
        unique = true,
        nullable = false
    )
    val userId: Long = userId
    @Column(
        unique = true,
        nullable = false
    )
    var childId: Long = childId

    fun changePrimaryChild(childId: Long){
        this.childId=childId
    }
}