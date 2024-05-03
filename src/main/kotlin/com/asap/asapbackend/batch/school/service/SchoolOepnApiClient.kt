package com.asap.asapbackend.batch.school.service

import com.asap.asapbackend.batch.school.model.NeisSchoolInfoJS
import com.asap.asapbackend.batch.school.model.SchoolResponse
import com.google.gson.Gson
import com.spot.refactoring.global.NotFoundSchoolException
import org.json.JSONObject
import org.json.XML
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.UriBuilder

@Service
class SchoolOepnApiClient {
    private var list_total_count: Int = 0

    //1~200
    fun getSchool1(): List<SchoolResponse>{
        val apiUrl = "http://openapi.seoul.go.kr:8088/4b78477274736f6d36386d505a614b/xml/neisSchoolInfoJS/1/200/"
        val client = WebClient.create(apiUrl)
        val result = client.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder.build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        val jsonResult = parseXmlData(result)
        val neisSchoolInfoJS= Gson().fromJson(jsonResult, NeisSchoolInfoJS::class.java)
        return result?.let { setDataList(neisSchoolInfoJS) } ?: throw RuntimeException()
    }
    //201~400
    fun getSchool2(): List<SchoolResponse>{
        val apiUrl = "http://openapi.seoul.go.kr:8088/4b78477274736f6d36386d505a614b/xml/neisSchoolInfoJS/201/400/"
        val client = WebClient.create(apiUrl)
        val result = client.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder.build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        val jsonResult = parseXmlData(result)
        val neisSchoolInfoJS= Gson().fromJson(jsonResult, NeisSchoolInfoJS::class.java)
        return result?.let { setDataList(neisSchoolInfoJS) } ?: throw RuntimeException()
    }
    //401~610
    fun getSchool3(): List<SchoolResponse>{
        val apiUrl = "http://openapi.seoul.go.kr:8088/4b78477274736f6d36386d505a614b/xml/neisSchoolInfoJS/401/${list_total_count}/"
        val client = WebClient.create(apiUrl)
        val result = client.get()
                .uri { uriBuilder: UriBuilder ->
                    uriBuilder.build()
                }
                .retrieve()
                .bodyToMono(String::class.java)
                .block()
        val jsonResult = parseXmlData(result)
        val neisSchoolInfoJS= Gson().fromJson(jsonResult, NeisSchoolInfoJS::class.java)
        return result?.let { setDataList(neisSchoolInfoJS) } ?: throw RuntimeException()
    }
    private fun setDataList(neisSchoolInfoJS: NeisSchoolInfoJS): List<SchoolResponse> {
        val resultList: MutableList<SchoolResponse> = mutableListOf()

        for (row in neisSchoolInfoJS.neisSchoolInfoJS) {
            resultList += SchoolResponse(
                    row.ATPT_OFCDC_SC_CODE,
                    row.SD_SCHUL_CODE,
                    row.SCHUL_NM,
                    row.ORG_RDNMA
            )
        }
        return resultList
    }
    private fun parseXmlData(xmlString: String?): String {
        val jsonObject = XML.toJSONObject(xmlString)
        try{
            val neisSchoolInfoJS = jsonObject.getJSONObject("neisSchoolInfoJS").getJSONArray("row")
            val resultJsonObject = JSONObject().put("neisSchoolInfoJS", neisSchoolInfoJS)
            list_total_count = jsonObject.getJSONObject("neisSchoolInfoJS").getInt("list_total_count")
            return resultJsonObject.toString(4)
        }catch (e: Exception){
            val message = jsonObject.getJSONObject("RESULT").getString("MESSAGE")
            throw NotFoundSchoolException(message)
        }
    }
}