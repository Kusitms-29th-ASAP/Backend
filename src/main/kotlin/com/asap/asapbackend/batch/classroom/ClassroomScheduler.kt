package com.asap.asapbackend.batch.classroom

import com.asap.asapbackend.domain.classroom.domain.service.ClassroomAppender
import com.asap.asapbackend.global.util.TransactionUtils
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ClassroomScheduler(
    private val classroomInfoProvider: ClassroomInfoProvider,
    private val classroomAppender: ClassroomAppender
) {
    @Scheduled(cron = "0 5 4 1 3 ?") // 매년 3월 1일 04:05:00에 실행
    fun addClassroom() {
        val batchSize = 100
        var startIndex = 0
        do {
            val classroomDataContainer = classroomInfoProvider.retrieveClassroomInfo(batchSize, startIndex)

            startIndex += batchSize

            TransactionUtils.writable {
                classroomAppender.addClassroom(classroomDataContainer.classroomInfo.toClassrooms())
            }
        } while (classroomDataContainer.hasNext)
    }
}