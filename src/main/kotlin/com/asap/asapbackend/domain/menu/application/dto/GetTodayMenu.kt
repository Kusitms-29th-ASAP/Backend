package com.asap.asapbackend.domain.menu.application.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Food

class GetTodayMenu {
    data class Response(
        val menus: List<Meal>
    )

    data class Meal(
        val food: String,
        val warning: Boolean
    )

    fun toMeal(foods: List<Food>?, allergies: List<Allergy?>): List<Meal> {
        return foods?.map {
            val warning = it.allergies.any { allergy ->
                allergies.contains(allergy)
            }
            Meal(
                food = it.name,
                warning = warning
            )
        } ?: emptyList()
    }
}