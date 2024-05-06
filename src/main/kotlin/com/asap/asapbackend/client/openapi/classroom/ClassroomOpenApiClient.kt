package com.asap.asapbackend.client.openapi.classroom

import com.asap.asapbackend.batch.classroom.ClassroomInfoProvider
import com.asap.asapbackend.client.openapi.classroom.dto.ClassroomOpenApiResponse
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import java.time.Year

@Component
class ClassroomOpenApiClient(
    private val schoolRepository: SchoolRepository
) : ClassroomInfoProvider {
    override fun retrieveClassroomInfo(batchSize: Int, startIndex: Int): ClassroomInfoProvider.ClassroomDataContainer {
        val endIndex = startIndex + batchSize
        val schools = schoolRepository.findAll().subList(startIndex, endIndex)
        val classroomInfoList = mutableListOf<ClassroomInfoProvider.ClassroomInfo>()
        schools.forEach { school ->
            val apiUrl = "https://open.neis.go.kr/hub/classInfo"
            val classroomInfoResult = WebClient.create(apiUrl).get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam("KEY", "32e897d4054342b19fd68dfb1b9ba621")
                        .queryParam("ATPT_OFCDC_SC_CODE", school.eduOfficeCode)
                        .queryParam("SD_SCHUL_CODE", school.schoolCode)
                        .queryParam("AY", Year.now())
                        .queryParam("Type", "json")
                        .build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .map {
                    jacksonObjectMapper().readValue(it, ClassroomOpenApiResponse::class.java)
                }
                .block()
            val classroomInfo = classroomInfoResult?.classInfo?.firstOrNull()
            classroomInfo?.row?.forEach { row ->
                classroomInfoList.add(row.toClassroomInfo(school))
            }
        }
        val hasNext = schoolRepository.count() > endIndex

        return ClassroomInfoProvider.ClassroomDataContainer(
            classroomInfo = classroomInfoList,
            hasNext = hasNext
        )
    }
}