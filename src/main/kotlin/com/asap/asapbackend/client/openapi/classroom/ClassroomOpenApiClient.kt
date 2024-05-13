package com.asap.asapbackend.client.openapi.classroom

import com.asap.asapbackend.batch.classroom.ClassroomInfoProvider
import com.asap.asapbackend.client.openapi.classroom.dto.ClassroomOpenApiResponse
import com.asap.asapbackend.client.openapi.vo.NeisOpenApiKey
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import java.time.Year

@Component
class ClassroomOpenApiClient(
    private val neisOpenApiKey: NeisOpenApiKey,
    private val schoolRepository: SchoolRepository,
    private val objectMapper: ObjectMapper
) : ClassroomInfoProvider {
    override fun retrieveClassroomInfo(batchSize: Int, pageNumber: Int): ClassroomInfoProvider.ClassroomDataContainer {
        val pageable = PageRequest.of(pageNumber, batchSize)
        val schools = schoolRepository.findAll(pageable)
        val hasNext = schools.hasNext()
        val classroomInfoList = schools.map { school ->
            val apiUrl = "https://open.neis.go.kr/hub/classInfo"
            val classroomInfoResult = WebClient.create(apiUrl).get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam("KEY", neisOpenApiKey.key)
                        .queryParam("ATPT_OFCDC_SC_CODE", school.eduOfficeCode)
                        .queryParam("SD_SCHUL_CODE", school.schoolCode)
                        .queryParam("AY", Year.now())
                        .queryParam("Type", "json")
                        .build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    objectMapper.readValue(it, ClassroomOpenApiResponse::class.java)
                }
                .block()
            classroomInfoResult?.classInfo?.flatMap { classInfo ->
                classInfo.row?.map { it.toClassroomInfo(school) } ?: emptyList()
            } ?: emptyList()
        }.flatten()
        return ClassroomInfoProvider.ClassroomDataContainer(
            classroomInfo = classroomInfoList,
            hasNext = hasNext
        )
    }
}