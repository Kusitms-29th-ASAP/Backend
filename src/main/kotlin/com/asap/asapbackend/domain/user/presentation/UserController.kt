package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.domain.user.application.UserService
import com.asap.asapbackend.domain.user.application.dto.CreateUser
import com.asap.asapbackend.domain.user.application.dto.GetUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping(UserApi.V1.BASE_URL)
    fun createUser(
        @RequestBody createUserRequest: CreateUser.Request
    ): CreateUser.Response {
        return userService.createUser(createUserRequest)
    }

    @GetMapping(UserApi.V1.BASE_URL)
    fun getUser() : GetUser.Response {
        return userService.getUser()
    }
}