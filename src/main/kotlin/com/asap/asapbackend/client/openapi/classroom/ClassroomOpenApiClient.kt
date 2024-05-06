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
        var endIndex = startIndex + batchSize
        val hasNext = schoolRepository.count() > endIndex
        if (!hasNext) {
            endIndex = schoolRepository.count().toInt()
        }
        val schools = schoolRepository.findAll().subList(startIndex, endIndex)
        val classroomInfoList: MutableList<ClassroomInfoProvider.ClassroomInfo> = mutableListOf()
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
            classroomInfoResult?.classInfo?.forEach { classInfo ->
                classInfo.row?.let { rows ->
                    classroomInfoList.addAll(rows.map { it.toClassroomInfo(school) })
                }
            }
        }
        return ClassroomInfoProvider.ClassroomDataContainer(
            classroomInfo = classroomInfoList,
            hasNext = hasNext
        )
    }
}