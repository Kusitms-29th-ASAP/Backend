package com.asap.asapbackend.global.jwt.registry.jpa

import com.asap.asapbackend.global.jwt.util.JwtRegistry
import org.springframework.stereotype.Component

@Component
class JwtJpaRegistry(
    private val tokenRepository: TokenRepository
) : JwtRegistry {

    override fun upsert(keyValue: Pair<Any, String>) {
        tokenRepository.save(Token(keyValue.first as Long, keyValue.second))
    }

    override fun isExists(value: String): Boolean {
        return tokenRepository.existsByToken(value)
    }


    override fun delete(value: String) {
        tokenRepository.deleteByToken(value)
    }
}