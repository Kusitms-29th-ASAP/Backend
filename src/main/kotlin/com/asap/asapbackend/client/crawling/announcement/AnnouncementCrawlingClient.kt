package com.asap.asapbackend.client.crawling.announcement

import com.asap.asapbackend.batch.announcement.AnnouncementInfoProvider
import com.asap.asapbackend.client.crawling.announcement.dto.AnnouncementCrawlingResponse
import com.asap.asapbackend.domain.announcement.domain.model.SchoolAnnouncementPage
import com.asap.asapbackend.domain.announcement.domain.repository.SchoolAnnouncementPageRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.time.Duration

@Component
class AnnouncementCrawlingClient(
    private val schoolAnnouncementPageRepository: SchoolAnnouncementPageRepository
) : AnnouncementInfoProvider {

    override fun retrieveAnnouncementInfo(
        batchSize: Int,
        pageNumber: Int
    ): AnnouncementInfoProvider.AnnouncementDataContainer {
        val schoolAnnouncements = schoolAnnouncementPageRepository.findAll(PageRequest.of(pageNumber, batchSize))
        val hasNext = schoolAnnouncements.hasNext()
        val announcementFluxes = schoolAnnouncements.map { schoolAnnouncement ->
            retrieveAnnouncementInfoFromCrawlingServer(schoolAnnouncement)
        }
        val announcementInfoList = mutableListOf<AnnouncementInfoProvider.AnnouncementInfo>()
        Flux.merge(announcementFluxes)
            .buffer(1000)
            .flatMap {
                Flux.fromIterable(it)
                    .doOnNext(announcementInfoList::add)
                    .then()
            }.blockLast()
        return AnnouncementInfoProvider.AnnouncementDataContainer(
            announcementInfo = announcementInfoList,
            hasNext = hasNext
        )
    }

    private fun retrieveAnnouncementInfoFromCrawlingServer(schoolAnnouncementPage: SchoolAnnouncementPage): Flux<AnnouncementInfoProvider.AnnouncementInfo> {
        return WebClient.create(CRAWLING_SERVER_URL)
            .get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("start_idx", 0)
                    .queryParam("batch_size", 0)
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
        private val CRAWLING_SERVER_URL = "http://localhost:3000/crawl"
    }
}