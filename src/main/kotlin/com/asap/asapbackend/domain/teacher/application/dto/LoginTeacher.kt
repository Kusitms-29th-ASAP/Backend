package com.asap.asapbackend.domain.teacher.application.dto

class LoginTeacher {

    data class Request(
        val username: String,
        val password: String
    ){
        fun convertLoginInfo(encode: (String) -> String): Pair<String, String> =
            Pair(username, encode(password))
    }


    data class Response(
        val accessToken: String
    )
}