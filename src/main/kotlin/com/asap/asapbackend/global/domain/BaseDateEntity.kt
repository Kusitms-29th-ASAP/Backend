package com.asap.asapbackend.global.domain

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
open class BaseDateEntity: BaseEntity(){

    lateinit var createdAt: LocalDateTime
        private set
    lateinit var updatedAt: LocalDateTime
        private set

    @PrePersist
    fun prePersist(){
        createdAt = LocalDateTime.now()
        updatedAt = LocalDateTime.now()
    }

    @PreUpdate
    fun update(){
        updatedAt = LocalDateTime.now()
    }
}