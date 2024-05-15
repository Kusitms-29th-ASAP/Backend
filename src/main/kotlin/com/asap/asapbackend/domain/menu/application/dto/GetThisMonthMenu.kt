package com.asap.asapbackend.domain.menu.application.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Menu
import java.time.LocalDate

class GetThisMonthMenu {
    data class Response(
        val menus: List<Food>
    )

    data class Food(
        val food: String,
        val warning: Boolean,
        val date: LocalDate
    )

    fun toFood(menus: List<Menu?>, allergies: List<Allergy>): List<Food> {
        return menus.flatMap {
            it?.foods?.map { food ->
                val warning = food.allergies.any { allergy ->
                    allergies.contains(allergy)
                }
                Food(food.name, warning, it.day)
            } ?: emptyList()
        }
    }
}