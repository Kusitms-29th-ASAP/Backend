package com.asap.asapbackend.client.openapi.timetable

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.client.openapi.timetable.dto.TimetableOpenApiResponse
import com.asap.asapbackend.client.openapi.vo.NeisOpenApiKey
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.Year
import java.time.format.DateTimeFormatter

@Component
class TimetableOpenApiClient(
    private val neisOpenApiKey: NeisOpenApiKey,
    private val schoolRepository: SchoolRepository,
    private val objectMapper: ObjectMapper
) : TimetableInfoProvider {
    override fun retrieveTimetableInfo(
        batchSize: Int, pageNumber: Int, day: LocalDate
    ): TimetableInfoProvider.TimetableDataContainer {
        val pageable = PageRequest.of(pageNumber, batchSize)
        val schools = schoolRepository.findAll(pageable)
        val hasNext = schools.hasNext()
        val timetableFluxes = schools.content.map { school ->
            getTimetableResponse(school, day)
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

    private fun getTimetableResponse(school: School, day: LocalDate): Flux<TimetableInfoProvider.TimetableResponse> {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val today = day.format(formatter)
        val apiUrl = "https://open.neis.go.kr/hub/elsTimetable"
        return WebClient.create(apiUrl).get()
            .uri { uriBuilder: UriBuilder ->
                uriBuilder
                    .queryParam("KEY", neisOpenApiKey.key)
                    .queryParam("ATPT_OFCDC_SC_CODE", school.eduOfficeCode)
                    .queryParam("SD_SCHUL_CODE", school.schoolCode)
                    .queryParam("AY", Year.now())
                    .queryParam("ALL_TI_YMD", today)
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
