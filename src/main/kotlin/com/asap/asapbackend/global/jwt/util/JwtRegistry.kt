package com.asap.asapbackend.global.jwt.util


interface JwtRegistry {

    fun upsert(keyValue: Pair<Any, String>)

    fun isExists(value: String): Boolean

    fun delete(value: String)

}