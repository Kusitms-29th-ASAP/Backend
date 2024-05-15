package com.asap.asapbackend.domain.menu.application.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Menu
import java.time.LocalDate

class GetThisMonthMenu {
    data class Response(
        val menus: Map<LocalDate, List<Meal>>
    )

    data class Meal(
        val food: String,
        val warning: Boolean,
    )

    fun toMeal(menus: Map<LocalDate, Menu?>, allergies: List<Allergy>): Map<LocalDate, List<Meal>> {
        return menus.mapValues { (_,it) ->
            it?.foods?.map { food ->
                val warning = food.allergies.any { allergy ->
                    allergies.contains(allergy)
                }
                Meal(food.name,warning)
            } ?: emptyList()
        }
    }
}