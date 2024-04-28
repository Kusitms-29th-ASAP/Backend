package com.asap.asapbackend.global.jwt


interface JwtRegistry {

    fun upsert(keyValue: Pair<Any, String>)

    fun isExists(value: String): Boolean

    fun delete(value: String)

}