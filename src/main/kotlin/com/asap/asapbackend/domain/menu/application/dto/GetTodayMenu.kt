package com.asap.asapbackend.domain.menu.application.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Food

class GetTodayMenu {
    data class Response(
        val menus: List<Menu>
    )

    data class Menu(
        val food: String,
        val warning: Boolean
    )

    fun toFood(foods: List<Food>?, allergies: List<Allergy?>): List<Menu> {
        return foods?.map {
            val warning = it.allergies.any { allergy ->
                allergies.contains(allergy)
            }
            Menu(
                food = it.name,
                warning = warning
            )
        } ?: emptyList()
    }
}