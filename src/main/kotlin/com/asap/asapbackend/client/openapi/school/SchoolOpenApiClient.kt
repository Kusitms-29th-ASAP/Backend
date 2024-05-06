package com.asap.asapbackend.client.openapi.school

import com.asap.asapbackend.batch.school.SchoolInfoProvider
import com.asap.asapbackend.client.openapi.school.dto.SchoolOpenApiResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class SchoolOpenApiClient : SchoolInfoProvider{
    override fun retrieveSchoolInfo(batchSize: Int, startIndex: Int): SchoolInfoProvider.SchoolDataContainer{
        val endIndex = startIndex + batchSize
        val apiUrl = "http://openapi.seoul.go.kr:8088/4b78477274736f6d36386d505a614b/json/neisSchoolInfoJS/$startIndex/$endIndex"
        val schoolInfoResult = WebClient.create(apiUrl).get()
            .retrieve()
            .bodyToMono(SchoolOpenApiResponse::class.java)
            .block()
        val neisSchoolInfoJS = schoolInfoResult?.neisSchoolInfoJS
        val hasNext = (schoolInfoResult?.neisSchoolInfoJS?.list_total_count ?: 0) > endIndex
        return SchoolInfoProvider.SchoolDataContainer(
            schoolInfo = neisSchoolInfoJS?.row?.map { it.toSchoolInfo() } ?: emptyList(),
            hasNext = hasNext
        )
    }
}