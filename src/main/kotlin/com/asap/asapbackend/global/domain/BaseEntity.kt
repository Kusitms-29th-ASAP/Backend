package com.asap.asapbackend.global.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
        get() {
            // TODO : 영속화 관련 예외 정의하기
            if(field == 0L) throw IllegalStateException("Entity must be persisted before using its id")
            return field
        }
}