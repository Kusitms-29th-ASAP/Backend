package com.asap.asapbackend.global.jwt.registry.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, Long> {
    fun existsByToken(token: String): Boolean

    fun deleteByToken(token: String)
}