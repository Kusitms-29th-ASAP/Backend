package com.asap.asapbackend.client.openapi.timetable

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.client.openapi.timetable.dto.TimetableOpenApiResponse
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import java.time.Year

@Component
class TimetableOpenApiClient(
    private val schoolRepository: SchoolRepository,
    private val objectMapper: ObjectMapper
) : TimetableInfoProvider {
    override fun retrieveTimetableInfo(batchSize: Int, pageNumber: Int): TimetableInfoProvider.TimetableDataContainer {
        val pageable = PageRequest.of(pageNumber, batchSize)
        val schools = schoolRepository.findAll(pageable)
        val hasNext = schools.hasNext()
        val timetableFluxes = schools.content.map { school ->
            getTimetableResponse(school)
        }
        val timetableInfoList = mutableListOf<TimetableInfoProvider.TimetableResponse>()
        Flux.merge(timetableFluxes)
            .buffer(1000)
            .flatMap {
                Flux.fromIterable(it)
                    .doOnNext(timetableInfoList::add)
                    .then()
            }.blockLast()

        return TimetableInfoProvider.TimetableDataContainer(
            timetableInfo = timetableInfoList,
            hasNext = hasNext
        )
    }

    private fun getTimetableResponse(school: School): Flux<TimetableInfoProvider.TimetableResponse> {
        val apiUrl = "https://open.neis.go.kr/hub/elsTimetable"
        return WebClient.create(apiUrl).get()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .queryParam("KEY", "32e897d4054342b19fd68dfb1b9ba621")
                    .queryParam("ATPT_OFCDC_SC_CODE", school.eduOfficeCode)
                    .queryParam("SD_SCHUL_CODE", school.schoolCode)
                    .queryParam("AY", Year.now())
                    .queryParam("TI_FROM_YMD", 20240507)
                    .queryParam("TI_TO_YMD", 20240507)
                    .queryParam("pSize", 1000)
                    .queryParam("Type", "json")
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
            .map {
                val timetableOpenApiResponse = objectMapper.readValue(it, TimetableOpenApiResponse::class.java)
                timetableOpenApiResponse?.elsTimetable?.flatMap { timetableInfo ->
                    timetableInfo.row?.map {
                        it.toTimetableInfo(school)
                    } ?: emptyList()
                } ?: emptyList()
            }
            .flatMapMany { Flux.fromIterable(it) }
    }
}
