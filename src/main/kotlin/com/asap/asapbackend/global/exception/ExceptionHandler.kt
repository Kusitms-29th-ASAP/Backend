package com.asap.asapbackend.global.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler { //아주 간단하게만 만든거라 추후 논의 후 변경 예정
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<HashMap<String,String>> {
        val result: HashMap<String, String> = HashMap<String, String>()
        result.put("message", e.message as String)
        return ResponseEntity<HashMap<String,String>>(result,HttpStatus.INTERNAL_SERVER_ERROR)
    }
}