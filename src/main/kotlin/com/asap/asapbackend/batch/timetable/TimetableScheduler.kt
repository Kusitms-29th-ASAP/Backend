package com.asap.asapbackend.batch.timetable

import com.asap.asapbackend.domain.timetable.domain.service.TimetableAppender
import com.asap.asapbackend.global.util.TransactionUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

@Component
class TimetableScheduler(
    private val timetableInfoProvider: TimetableInfoProvider,
    private val timetableAppender: TimetableAppender
) {
//    @Scheduled(cron = "0 0 5 * * MON") //매주 월요일 05:00:00에 실행
    fun addTimetable() {
        val batchSize = 100
        var pageNumber = 0

        val startTime = System.currentTimeMillis()
        var today = LocalDate.now()
        var day = 0
        while (day < 5) {
            do {

                val timetableDataContainer = timetableInfoProvider.retrieveTimetableInfo(batchSize, pageNumber, today)

                pageNumber++

                TransactionUtils.writable {
                    timetableAppender.addSubjectAndTimetable(timetableDataContainer.timetableInfo)
                }
            } while (timetableDataContainer.hasNext)
            pageNumber = 0
            today = today.plusDays(1)
            day++
        }
        val endTime = System.currentTimeMillis()
        logger.info { "addTimetable end, elapsed time: ${endTime - startTime}" }
    }
}