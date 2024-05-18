package com.asap.asapbackend.client.crawling.announcement

import com.asap.asapbackend.batch.announcement.SchoolAnnouncementInfoProvider
import com.asap.asapbackend.client.crawling.announcement.dto.AnnouncementCrawlingResponse
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementPageRepository
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.time.Duration

@Component
class SchoolAnnouncementCrawlingClient(
    private val schoolAnnouncementPageRepository: SchoolAnnouncementPageRepository,
    private val schoolAnnouncementRepository: SchoolAnnouncementRepository
) : SchoolAnnouncementInfoProvider {

    override fun retrieveAnnouncementInfo(
        batchSize: Int,
        pageNumber: Int
    ): SchoolAnnouncementInfoProvider.SchoolAnnouncementDataContainer {
        val schoolAnnouncements = schoolAnnouncementPageRepository.findAll(PageRequest.of(pageNumber, batchSize))
        val hasNext = schoolAnnouncements.hasNext()
        val announcementFluxes = schoolAnnouncements.map { schoolAnnouncement ->
            val startIdx = schoolAnnouncementRepository.findLastIndex()
            retrieveAnnouncementInfoFromCrawlingServer(schoolAnnouncement, startIdx, batchSize)
        }
        val schoolAnnouncementInfoList = mutableListOf<SchoolAnnouncementInfoProvider.SchoolAnnouncementInfo>()
        Flux.merge(announcementFluxes)
            .buffer(1000)
            .flatMap {
                Flux.fromIterable(it)
                    .doOnNext(schoolAnnouncementInfoList::add)
                    .then()
            }.blockLast()
        return SchoolAnnouncementInfoProvider.SchoolAnnouncementDataContainer(
            schoolAnnouncementInfo = schoolAnnouncementInfoList,
            hasNext = hasNext
        )
    }

    private fun retrieveAnnouncementInfoFromCrawlingServer(schoolAnnouncementPage: SchoolAnnouncementPage, startIdx: Int, batchSize: Int): Flux<SchoolAnnouncementInfoProvider.SchoolAnnouncementInfo> {
        return WebClient.create(CRAWLING_SERVER_URL)
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("start_idx", startIdx)
                    .queryParam("batch_size", batchSize)
                    .queryParam("element_school_url", schoolAnnouncementPage.schoolAnnouncementPageUrl)
                    .build()
            }
            .retrieve()
            .bodyToMono(AnnouncementCrawlingResponse::class.java)
            .timeout(Duration.ofMinutes(10))
            .map{
                it.convertToAnnouncement(schoolAnnouncementPage)
            }.flatMapMany { Flux.fromIterable(it)}
    }

    companion object{
        private val CRAWLING_SERVER_URL = "http://crawling.ncp.simproject.kr:3000/crawl"
    }
}