package com.asap.asapbackend.client.openapi.menu.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Food
import com.asap.asapbackend.domain.menu.domain.model.Menu
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
    fun toMenu(school: School): Menu {
        return Menu(

            schoolId = school.id,
            foods = convertToFoods(DDISH_NM),
            day = LocalDate.parse(MLSV_YMD, DateTimeFormatter.BASIC_ISO_DATE)
        )
    }

    private fun convertToFoods(menus: String): List<Food> {
        val foodList = mutableListOf<Food>()

        val menuItems = menus.split("<br/>")
        menuItems.forEach { menuItem ->
            val nameAndAllergies = menuItem.split(" ")

            val foodName = menuItem.removeSuffix(" " + nameAndAllergies.last())
            val allergiesStr = nameAndAllergies.last().removePrefix("(").removeSuffix(")")

            val allergyNumbers: List<Int> = if (allergiesStr.isNotEmpty()) {
                if (!allergiesStr.contains(".")) {
                    listOf(allergiesStr.toInt())
                } else {
                    allergiesStr.split(".").map { it.toInt() }
                }
            } else {
                emptyList()
            }

            val allergies = allergyNumbers.mapNotNull { number ->
                Allergy.entries.find { it.number == number }
            }

            val food = Food(foodName, allergies)
            foodList.add(food)
        }
        return foodList
    }
}
