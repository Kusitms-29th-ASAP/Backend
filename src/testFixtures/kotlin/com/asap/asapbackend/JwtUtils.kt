package com.asap.asapbackend

import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.jackson.io.JacksonDeserializer
import javax.crypto.SecretKey

fun generateBasicParser(
    key: SecretKey,
    classTypeMap: Map<String, Class<*>>
): JwtParser {
    return Jwts.parser()
        .json(JacksonDeserializer(classTypeMap))
        .verifyWith(key)
        .build()
}