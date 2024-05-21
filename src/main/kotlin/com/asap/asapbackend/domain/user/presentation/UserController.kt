package com.asap.asapbackend.domain.user.presentation

import com.asap.asapbackend.domain.user.application.UserService
import com.asap.asapbackend.domain.user.application.dto.ChangeUserInfo
import com.asap.asapbackend.domain.user.application.dto.CreateUser
import com.asap.asapbackend.domain.user.application.dto.GetUser
import org.springframework.web.bind.annotation.*

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

    @PutMapping(UserApi.V1.BASE_URL)
    fun changeUserInfo(@RequestBody changeUserInfoRequest: ChangeUserInfo.Request) {
        return userService.changeUserInfo(changeUserInfoRequest)
    }
}