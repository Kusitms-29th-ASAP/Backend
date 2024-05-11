package com.asap.asapbackend.client.openapi.timetable

import com.asap.asapbackend.batch.timetable.TimetableInfoProvider
import com.asap.asapbackend.client.openapi.timetable.dto.TimetableOpenApiResponse
import com.asap.asapbackend.domain.classroom.domain.repository.ClassroomRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import java.time.Year

@Component
class TimetableOpenApiClient(
    private val classroomRepository: ClassroomRepository,
    private val objectMapper: ObjectMapper
) : TimetableInfoProvider {
    override fun retrieveTimetableInfo(batchSize: Int, pageNumber: Int): TimetableInfoProvider.TimetableDataContainer {
        val pageable = PageRequest.of(pageNumber, batchSize)
        val classrooms = classroomRepository.findAll(pageable)
        val hasNext = classrooms.hasNext()
        val timetableInfoList = classrooms.map { classroom ->
            val apiUrl = "https://open.neis.go.kr/hub/elsTimetable"
            val timetableInfoResult = WebClient.create(apiUrl).get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam("KEY", "32e897d4054342b19fd68dfb1b9ba621")
                        .queryParam("ATPT_OFCDC_SC_CODE", classroom.school.eduOfficeCode)
                        .queryParam("SD_SCHUL_CODE", classroom.school.schoolCode)
                        .queryParam("AY", Year.now())
                        .queryParam("GRADE", classroom.grade.level)
                        .queryParam("CLASS_NM", classroom.className)
                        .queryParam("TI_FROM_YMD", 20240507)
                        .queryParam("TI_TO_YMD", 20240507)
                        .queryParam("Type", "json")
                        .build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    objectMapper.readValue(it, TimetableOpenApiResponse::class.java)
                }
                .block()
            timetableInfoResult?.elsTimetable?.flatMap { timetableInfo ->
                timetableInfo.row?.map {
                    it.toTimetableInfo(classroom)
                } ?: emptyList()
            } ?: emptyList()
        }.flatten()
        return TimetableInfoProvider.TimetableDataContainer(
            timetableInfo = timetableInfoList,
            hasNext = hasNext
        )
    }
}