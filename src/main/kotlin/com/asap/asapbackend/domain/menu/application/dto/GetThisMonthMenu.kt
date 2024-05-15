package com.asap.asapbackend.domain.menu.application.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Menu
import java.time.LocalDate

class GetThisMonthMenu {
    data class Response(
        val menus: List<MealDetail>
    )

    data class MealDetail(
        val date: LocalDate,
        val foods: List<Meal>
    )

    data class Meal(
        val food: String,
        val warning: Boolean,
    )

    fun toMeal(menus: List<Menu>, allergies: List<Allergy>): List<MealDetail> {
        val menuDataMap = menus.groupBy {
            it.day
        }

        return menuDataMap.map { (day, menus) ->
            val foods = menus.flatMap { menu ->
                menu.foods.map {
                    val warning = it.allergies.any { allergy ->
                        allergies.contains(allergy)
                    }
                    Meal(it.name, warning)
                }
            }
            MealDetail(day, foods)
        }
    }
}