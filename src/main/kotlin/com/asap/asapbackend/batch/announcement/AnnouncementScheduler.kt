package com.asap.asapbackend.batch.announcement

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.service.AnnouncementAppender
import com.asap.asapbackend.domain.announcement.domain.service.AnnouncementReader
import com.asap.asapbackend.global.util.ImageToTextConverter
import com.asap.asapbackend.global.util.TextSummaryHelper
import com.asap.asapbackend.global.util.TransactionUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class AnnouncementScheduler(
    private val schoolAnnouncementInfoProvider: SchoolAnnouncementInfoProvider,
    private val educationOfficeAnnouncementInfoProvider: EducationOfficeAnnouncementInfoProvider,
    private val imageToTextConverter: ImageToTextConverter,
    private val textSummaryHelper: TextSummaryHelper,
    private val announcementAppender: AnnouncementAppender,
    private val announcementReader: AnnouncementReader
) {
    //     매 평일 9시부터 18시까지 1시간마다 실행
    @Scheduled(cron = "0 0 9-18 * * MON-FRI")
    fun addAnnouncement() {
        val batchSize = 100
        var pageNumber = 0
        do {
            val announcementDataContainer =
                schoolAnnouncementInfoProvider.retrieveAnnouncementInfo(batchSize, pageNumber)

            pageNumber++

            val announcements = announcementDataContainer.schoolAnnouncementInfo.groupBy { it.index }
                .map {
                    it.value.reduce { acc, educationOfficeAnnouncementInfo ->
                        acc.copy(
                            title = acc.title.isEmpty().let { educationOfficeAnnouncementInfo.title },
                            imageUrls = acc.imageUrls + educationOfficeAnnouncementInfo.imageUrls.sorted()
                        )
                    }
                }
                .map {
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
                announcementAppender.addSchoolAnnouncements(announcements.toSet())
            }
        } while (announcementDataContainer.hasNext)
    }


    @Scheduled(cron = "0 0 9-18 * * MON-FRI")
    fun addEducationOfficeAnnouncement() {
        val batchSize = 100
        var startIdx = announcementReader.getLastOfficeEducationAnnouncementId()
        do {
            val announcementDataContainer =
                educationOfficeAnnouncementInfoProvider.retrieveAnnouncementInfo(batchSize, startIdx)

            startIdx += batchSize

            val announcements = announcementDataContainer.educationOfficeAnnouncementInfo.groupBy { it.index }
                .map {
                    it.value.reduce { acc, educationOfficeAnnouncementInfo ->
                        acc.copy(
                            title = acc.title,
                            imageUrls = acc.imageUrls + educationOfficeAnnouncementInfo.imageUrls.sorted()
                        )
                    }
                }
                .map {
                    val textFromImage = imageToTextConverter.convertImageToText(it.imageUrls)
                    var summarizedText = listOf<String>()
                    if (textFromImage.isNotEmpty()) {
                        summarizedText = textSummaryHelper.summarizeText(textFromImage)
                    }
                    return@map EducationOfficeAnnouncement(
                        idx = it.index,
                        title = it.title,
                        imageUrls = it.imageUrls,
                        summaries = summarizedText
                    )
                }

            TransactionUtils.writable {
                announcementAppender.addEducationOfficeAnnouncements(announcements.toSet())
            }
        } while (announcementDataContainer.hasNext)
    }
}