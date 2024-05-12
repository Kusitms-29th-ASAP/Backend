package com.asap.asapbackend

import com.asap.asapbackend.global.jwt.util.TokenExtractor
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


const val TOKEN_HEADER_NAME = TokenExtractor.Constants.HEADER
const val TOKEN_PREFIX = TokenExtractor.Constants.PREFIX