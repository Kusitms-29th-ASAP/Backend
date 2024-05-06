package com.asap.asapbackend.batch.classroom

import com.asap.asapbackend.batch.classroom.dto.ClassInfo
import com.asap.asapbackend.batch.classroom.dto.ClassroomResponse
import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.domain.school.domain.repository.SchoolRepository
import com.google.gson.Gson
import org.json.JSONObject
import org.json.XML
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

@Service
class ClassroomOpenApiClient(
    private val schoolRepository: SchoolRepository
) {

    fun getClassroom(): List<ClassroomResponse> {
        val schools = schoolRepository.findAll()
        val resultList: MutableList<ClassroomResponse> = mutableListOf()
        schools.forEach { school ->
            val apiUrl = "https://open.neis.go.kr/hub/classInfo"
            val client = WebClient.create(apiUrl)
            val result = client.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                        .queryParam("KEY", "32e897d4054342b19fd68dfb1b9ba621")
                        .queryParam("ATPT_OFCDC_SC_CODE", school.eduOfiiceCode)
                        .queryParam("SD_SCHUL_CODE", school.schoolCode)
                        .queryParam("AY", "2024")
                        .build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
            val jsonObject = XML.toJSONObject(result)
            if (jsonObject.has("classInfo")) {
                val classInfo = jsonObject.getJSONObject("classInfo").getJSONArray("row")
                val resultJsonObject = JSONObject().put("classInfo", classInfo)
                val jsonResult = resultJsonObject.toString(4)
                val info = Gson().fromJson(jsonResult, ClassInfo::class.java)
                resultList.addAll(setDataList(info, school))
            }
        }
        return resultList
    }

    private fun setDataList(classInfo: ClassInfo, school: School): List<ClassroomResponse> {
        val resultList: MutableList<ClassroomResponse> = mutableListOf()

        for (row in classInfo.classInfo) {
            resultList += ClassroomResponse(
                row.GRADE,
                row.CLASS_NM,
                school
            )
        }
        return resultList
    }
}