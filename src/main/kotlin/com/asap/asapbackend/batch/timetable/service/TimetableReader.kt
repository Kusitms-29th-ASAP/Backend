package com.asap.asapbackend.batch.timetable.service

import com.asap.asapbackend.batch.timetable.model.ElsTimetable
import com.asap.asapbackend.domain.timetable.domain.model.*
import com.google.gson.Gson
import com.spot.refactoring.global.NotFoundTimetableException
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder
import org.json.XML

@Service
class TimetableReader {
    fun getTimetable(): List<TimetableResponse> {
        val apiUrl = "https://open.neis.go.kr/hub/elsTimetable"
        val client = WebClient.create(apiUrl)
        val result = client.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder
                            .queryParam("KEY", "32e897d4054342b19fd68dfb1b9ba621")
                            .queryParam("ATPT_OFCDC_SC_CODE", "T10")
                            .queryParam("SD_SCHUL_CODE", "9299048")
                            .queryParam("AY", "2024")
                            .queryParam("SEM", "1")
                            .queryParam("GRADE", "1")
                            .queryParam("CLASS_N", "1")
                            .queryParam("TI_FROM_YMD", "20240429")
                            .queryParam("TI_TO_YMD", "20240503")
                            .build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        val jsonResult = parseXmlData(result)
        val elsTimetable = Gson().fromJson(jsonResult, ElsTimetable::class.java)
        return result?.let { setDataList(elsTimetable) } ?: throw RuntimeException()
    }

    private fun setDataList(elsTimetable: ElsTimetable): List<TimetableResponse> {
        val resultList: MutableList<TimetableResponse> = mutableListOf()

        for (row in elsTimetable.elsTimetable) {
            resultList += TimetableResponse(
                    row.ALL_TI_YMD,
                    row.PERIO,
                    row.ITRT_CNTNT
            )
        }
        return resultList
    }

    private fun parseXmlData(xmlString: String?): String {
        val jsonObject = XML.toJSONObject(xmlString)
        try{
            val elsTimetableArray = jsonObject.getJSONObject("elsTimetable").getJSONArray("row")
            val resultJsonObject = JSONObject().put("elsTimetable", elsTimetableArray)
            return resultJsonObject.toString(4)
        }catch (e: Exception){
            val message = jsonObject.getJSONObject("RESULT").getString("MESSAGE")
            throw NotFoundTimetableException(message)
        }
    }
}