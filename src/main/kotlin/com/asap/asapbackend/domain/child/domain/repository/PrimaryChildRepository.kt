package com.asap.asapbackend.domain.child.domain.repository

import com.asap.asapbackend.domain.child.domain.model.Child
import com.asap.asapbackend.domain.child.domain.model.PrimaryChild
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PrimaryChildRepository: JpaRepository<PrimaryChild, Long> {
    fun existsByUserId(userId: Long): Boolean

    @Query("""
    select c
    from Child c
    join PrimaryChild pc on c.id = pc.childId
    where pc.userId = :userId
    """)
    fun findChildByUserId(userId: Long): Child
}