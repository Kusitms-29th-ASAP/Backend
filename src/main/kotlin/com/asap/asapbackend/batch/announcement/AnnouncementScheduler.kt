package com.asap.asapbackend.batch.announcement

import com.asap.asapbackend.domain.announcement.domain.model.EducationOfficeAnnouncement
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncement
import com.asap.asapbackend.domain.announcement.domain.service.SchoolAnnouncementAppender
import com.asap.asapbackend.domain.announcement.domain.service.SchoolAnnouncementReader
import com.asap.asapbackend.global.util.ImageToTextConverter
import com.asap.asapbackend.global.util.TextKeywordExtractor
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
    private val textKeywordExtractor: TextKeywordExtractor,
    private val schoolAnnouncementAppender: SchoolAnnouncementAppender,
    private val schoolAnnouncementReader: SchoolAnnouncementReader
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

            val announcements = announcementDataContainer.schoolAnnouncementInfo
                .map {
                    val (summarizedText, keywords) = convertImageToSummariesAndKeywords(it.imageUrls)
                    return@map SchoolAnnouncement(
                        schoolAnnouncementPageId = it.schoolAnnouncementPage.id,
                        index = it.index,
                        title = it.title,
                        imageUrls = it.imageUrls,
                        summaries = summarizedText,
                        keywords = keywords
                    )
                }

            TransactionUtils.writable {
                schoolAnnouncementAppender.addSchoolAnnouncements(announcements.toSet())
            }
        } while (announcementDataContainer.hasNext)
    }


    @Scheduled(cron = "0 0 9-18 * * MON-FRI")
    fun addEducationOfficeAnnouncement() {
        val batchSize = 100
        var startIdx = schoolAnnouncementReader.getLastOfficeEducationAnnouncementId()
        do {
            val announcementDataContainer =
                educationOfficeAnnouncementInfoProvider.retrieveAnnouncementInfo(batchSize, startIdx)

            startIdx += batchSize

            val announcements = announcementDataContainer.educationOfficeAnnouncementInfo
                .map {
                    val (summarizedText, keywords) = convertImageToSummariesAndKeywords(it.imageUrls)
                    return@map EducationOfficeAnnouncement(
                        idx = it.index,
                        title = it.title,
                        imageUrls = it.imageUrls,
                        summaries = summarizedText,
                        keywords = keywords
                    )
                }

            TransactionUtils.writable {
                schoolAnnouncementAppender.addEducationOfficeAnnouncements(announcements.toSet())
            }
        } while (announcementDataContainer.hasNext)
    }

    private fun convertImageToSummariesAndKeywords(imageUrls: List<String>): Pair<List<String>, List<String>> {
        val textFromImage = imageToTextConverter.convertImageToText(imageUrls)
        val summarizedText =
            textFromImage.takeIf { it.isNotEmpty() }?.let { textSummaryHelper.summarizeText(it) }
                ?: listOf()
        val keywords = textFromImage.takeIf { it.isNotEmpty() }?.let { textKeywordExtractor.extractKeywords(it) }
            ?: listOf()
        return summarizedText to keywords
    }
}