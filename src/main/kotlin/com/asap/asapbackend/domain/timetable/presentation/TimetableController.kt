package com.asap.asapbackend.domain.timetable.presentation

import com.asap.asapbackend.domain.timetable.application.TimetableService
import com.asap.asapbackend.domain.timetable.application.dto.ReadThisWeekTimetable
import com.asap.asapbackend.domain.timetable.application.dto.ReadTodayTimetable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TimetableController(
    private val timetableService: TimetableService
) {
    @GetMapping(TimetableApi.V1.TODAY)
    fun getTodayTimetable(): ReadTodayTimetable.Response {
        return timetableService.getTodayTimetable()
    }

    @GetMapping(TimetableApi.V1.WEEK)
    fun getThisWeekTimetable(): ReadThisWeekTimetable.Response {
        return timetableService.getThisWeekTimetable()
    }
}