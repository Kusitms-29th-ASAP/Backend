package com.asap.asapbackend.domain.timetable.application

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TimetableController (
        private val timetableService: TimetableService
){
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul") //1시간마다
    @GetMapping("/timetable")
    fun getTimetable() {
        println(timetableService.getTimetable()) //연관관계 설정 후 db에 넣는 것으로 변경 예정
    }
}