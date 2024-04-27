package com.asap.asapbackend.global.jwt

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

@Component
class JwtKeyFactory(
    private val jwtProperties: JwtProperties
) {

    fun generateKey(): SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secret))
}