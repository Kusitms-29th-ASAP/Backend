package com.asap.asapbackend.batch.event

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.modulith.events.IncompleteEventPublications
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {  }

@Component
class IncompleteEventRepublicationHandler(
    private val applicationEventMulticaster: IncompleteEventPublications
) {

    // 매 시간마다 실행
    @Scheduled(cron = "0 0 * * * ?")
    fun resubmitIncompletePublications() {
        applicationEventMulticaster.resubmitIncompletePublications { true }
    }

}