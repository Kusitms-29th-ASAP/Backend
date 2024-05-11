package com.asap.asapbackend.batch.timetable

import com.asap.asapbackend.domain.timetable.domain.service.TimetableAppender
import com.asap.asapbackend.global.util.TransactionUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TimetableScheduler(
    private val timetableInfoProvider: TimetableInfoProvider,
    private val timetableAppender: TimetableAppender
) {
    @Scheduled(cron = "0 0 5 * * MON") //매주 월요일 05:00:00에 실행
    fun addTimetable() {
        val batchSize = 10
        var pageNumber = 0
        do {
            val timetableDataContainer = timetableInfoProvider.retrieveTimetableInfo(batchSize, pageNumber)

            pageNumber++

            TransactionUtils.writable {
                timetableAppender.addSubjectAndTimetable(timetableDataContainer.timetableInfo)
            }
        } while (timetableDataContainer.hasNext)
    }
}