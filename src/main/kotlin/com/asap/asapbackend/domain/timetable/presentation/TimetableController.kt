package com.asap.asapbackend.domain.timetable.presentation

import com.asap.asapbackend.domain.timetable.application.TimetableService
import com.asap.asapbackend.domain.timetable.application.dto.GetThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.GetTodayTimetable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TimetableController(
    private val timetableService: TimetableService
) {
    @GetMapping(TimetableApi.V1.TODAY)
    fun getTodayTimetable(): GetTodayTimetable.Response {
        return timetableService.getTodayTimetable()
    }

    @GetMapping(TimetableApi.V1.WEEK)
    fun getThisWeekTimetable(): GetThisWeekTimetable.Response {
        return timetableService.getThisWeekTimetable()
    }
}