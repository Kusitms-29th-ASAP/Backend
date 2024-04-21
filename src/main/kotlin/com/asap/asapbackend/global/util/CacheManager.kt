package com.asap.asapbackend.global.util

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class CacheManager {

    companion object{
        private val cache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build<String, Any>()


        fun <R> cacheByKey(key: String,  body: () -> R): R {
            cache.getIfPresent(key)?.let {
                return it as R
            } ?: run {
                val result = body()
                cache.put(key, result)
                return result
            }
        }
    }
}