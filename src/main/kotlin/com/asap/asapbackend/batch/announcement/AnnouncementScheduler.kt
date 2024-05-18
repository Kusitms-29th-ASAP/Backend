package com.asap.asapbackend.batch.announcement

import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.service.SchoolAnnouncementAppender
import com.asap.asapbackend.global.util.ImageToTextConverter
import com.asap.asapbackend.global.util.TextSummaryHelper
import com.asap.asapbackend.global.util.TransactionUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class AnnouncementScheduler(
    private val announcementInfoProvider: AnnouncementInfoProvider,
    private val imageToTextConverter: ImageToTextConverter,
    private val textSummaryHelper: TextSummaryHelper,
    private val schoolAnnouncementAppender: SchoolAnnouncementAppender
) {
    //     매 평일 9시부터 18시까지 1시간마다 실행
    @Scheduled(cron = "0 0 9-18 * * MON-FRI")
    fun addAnnouncement() {
        val batchSize = 100
        var pageNumber = 0
        logger.info { "Start addAnnouncement" }
        do {
            val announcementDataContainer = announcementInfoProvider.retrieveAnnouncementInfo(batchSize, pageNumber)

            pageNumber++

            val announcements = announcementDataContainer.announcementInfo.map {
                val textFromImage = imageToTextConverter.convertImageToText(it.imageUrls)
                var summarizedText = listOf<String>()
                if (textFromImage.isNotEmpty()) {
                    summarizedText = textSummaryHelper.summarizeText(textFromImage)
                }
                return@map SchoolAnnouncement(
                    schoolAnnouncementPage = it.schoolAnnouncementPage,
                    index = it.index,
                    title = it.title,
                    imageUrls = it.imageUrls,
                    summaries = summarizedText
                )
            }

             TransactionUtils.writable {
                 schoolAnnouncementAppender.addAnnouncements(announcements.toSet())
             }
        } while (announcementDataContainer.hasNext)
        logger.info { "End addAnnouncement" }
    }
}