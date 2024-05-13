package com.asap.asapbackend.domain.child.domain.repository

import com.asap.asapbackend.domain.child.domain.model.PrimaryChild
import org.springframework.data.jpa.repository.JpaRepository

interface PrimaryChildRepository: JpaRepository<PrimaryChild, Long> {
    fun existsByUserId(userId: Long): Boolean

    fun findByUserId(userId: Long): PrimaryChild?
}