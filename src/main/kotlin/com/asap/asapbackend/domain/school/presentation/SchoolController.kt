package com.asap.asapbackend.domain.school.presentation

import com.asap.asapbackend.domain.school.application.SchoolService
import com.asap.asapbackend.domain.school.application.dto.GetSchools
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SchoolController(
    private val schoolService: SchoolService
) {
    @GetMapping(SchoolApi.V1.BASE_URL)
    fun getSchool(@RequestParam keyword:String): GetSchools.Response{
        return schoolService.getSchools(keyword)
    }
}