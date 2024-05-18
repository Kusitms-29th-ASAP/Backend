package com.asap.asapbackend.client.crawling.announcement

import com.asap.asapbackend.batch.announcement.EducationOfficeAnnouncementInfoProvider
import com.asap.asapbackend.client.crawling.announcement.dto.AnnouncementCrawlingResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration

@Component
class EducationOfficeAnnouncementCrawlingClient(

) : EducationOfficeAnnouncementInfoProvider {
    override fun retrieveAnnouncementInfo(
        batchSize: Int,
        startIndex: Int
    ): EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementDataContainer {
        val announcements = sendCrawlingRequest(startIndex, batchSize.toLong())
        return EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementDataContainer(
            educationOfficeAnnouncementInfo = announcements,
            hasNext = (announcements.size == batchSize)
        )
    }

    private fun sendCrawlingRequest(startIdx: Int, batchSize: Long): List<EducationOfficeAnnouncementInfoProvider.EducationOfficeAnnouncementInfo> {
        return WebClient.create(CRAWLING_SERVER_URL)
            .get()
            .uri {
                it.queryParam("element_school_url", OFFICE_EDUCATION_URL)
                    .queryParam("start_idx", startIdx)
                    .queryParam("batch_size", batchSize)
                    .build()
            }
            .retrieve()
            .bodyToMono(AnnouncementCrawlingResponse::class.java)
            .timeout(Duration.ofMinutes(20))
            .map {
                it.convertToAnnouncement()
            }
            .block() ?: emptyList()
    }


    companion object{
        private const val OFFICE_EDUCATION_URL = "https://yangwonsoop.sen.es.kr/192786/subMenu.do"
        private const val CRAWLING_SERVER_URL = "http://crawling.ncp.simproject.kr:3000/crawl"
    }
}