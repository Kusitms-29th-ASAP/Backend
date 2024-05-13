package com.asap.asapbackend.client.openapi.menu.dto

import com.asap.asapbackend.batch.menu.MenuInfoProvider
import com.asap.asapbackend.domain.school.domain.model.School
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MenuOpenApiResponse(
    val mealServiceDietInfo: List<MealInfo>?,
    val RESULT: Result?

)

data class MealInfo(
    val head: List<Head>?,
    val row: List<Row>?
)

data class Head(
    val list_total_count: Int?,
    val RESULT: Result?
)

data class Result(
    val CODE: String,
    val MESSAGE: String
)

data class Row(
    val MLSV_YMD: String,
    val DDISH_NM: String
) {
    fun toMenuInfo(school: School): MenuInfoProvider.MenuResponse {
        return MenuInfoProvider.MenuResponse(
            school = school,
            menu = DDISH_NM,
            day = LocalDate.parse(MLSV_YMD, DateTimeFormatter.BASIC_ISO_DATE)
        )
    }
}
