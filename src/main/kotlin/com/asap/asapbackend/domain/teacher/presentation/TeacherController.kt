package com.asap.asapbackend.domain.teacher.presentation

import com.asap.asapbackend.domain.teacher.application.TeacherService
import com.asap.asapbackend.domain.teacher.application.dto.CreateTeacher
import com.asap.asapbackend.domain.teacher.application.dto.LoginTeacher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TeacherController(
    private val teacherService: TeacherService
) {

    @PostMapping(TeacherApi.V1.BASE_URL)
    fun createTeacher(@RequestBody request: CreateTeacher.Request){
        teacherService.createTeacher(request)
    }


    @PostMapping(TeacherApi.V1.LOGIN)
    fun loginTeacher(@RequestBody request: LoginTeacher.Request) : LoginTeacher.Response{
        return teacherService.loginTeacher(request)
    }
}