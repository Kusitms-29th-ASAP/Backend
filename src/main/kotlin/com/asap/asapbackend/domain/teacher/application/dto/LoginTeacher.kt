package com.asap.asapbackend.domain.teacher.application.dto

class LoginTeacher {

    data class Request(
        val username: String,
        val password: String
    ){
        fun convertLoginInfo(): Pair<String, String> =
            Pair(username, password)
    }


    data class Response(
        val accessToken: String
    )
}