package com.asap.asapbackend.batch.school

import com.asap.asapbackend.domain.school.domain.service.SchoolAppender
import com.asap.asapbackend.global.util.TransactionUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class SchoolScheduler(
    private val schoolInfoProvider: SchoolInfoProvider,
    private val schoolAppender: SchoolAppender
) {
    @Scheduled(cron = "0 0 4 1 3 ?") // 매년 3월 1일 04:00:00에 실행
//    @Scheduled(fixedRate = 500000) // 500초마다 실행
    fun addSchool() {
        val batchSize = 100
        var startIndex = 1
        logger.info { "start scheduler" }
        do {
            val schoolDataContainer = schoolInfoProvider.retrieveSchoolInfo(batchSize, startIndex)

            startIndex += batchSize

            TransactionUtils.writable {
                schoolAppender.appendUniqueSchool(schoolDataContainer.schoolInfo.toSchools())
            }
        }while (schoolDataContainer.hasNext)
    }
}