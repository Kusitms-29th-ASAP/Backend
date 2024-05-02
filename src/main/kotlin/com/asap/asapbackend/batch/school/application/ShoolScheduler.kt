package com.asap.asapbackend.batch.school.application

import jakarta.annotation.PostConstruct
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ShoolScheduler(
        private val schoolService: SchoolService
) {
    //@Scheduled(cron = "0 0 4 1 3 ?") // 매년 3월 1일 04:00:00에 실행 //매년 학교 수가 달라져서 보류
    @GetMapping("/addSchool")
    fun addSchool() {
        schoolService.addSchool()
    }
}