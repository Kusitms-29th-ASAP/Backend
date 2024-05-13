package com.asap.asapbackend.domain.menu.domain.service

import com.asap.asapbackend.batch.menu.MenuInfoProvider
import com.asap.asapbackend.domain.menu.domain.model.Allergy
import com.asap.asapbackend.domain.menu.domain.model.Food
import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.domain.menu.domain.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuAppender(
    private val menuRepository: MenuRepository
) {
    fun addMenu(menus: List<MenuInfoProvider.MenuResponse>) {
        val menuList = mutableListOf<Menu>()
        menus.forEach {
            val foodList = extractMenu(it.menu)
            val menu = Menu(
                it.school, it.day, foodList
            )
            menuList.add(menu)
        }
        menuRepository.saveAll(menuList)
    }

    private fun extractMenu(menu: String): List<Food> {
        val foodList = mutableListOf<Food>()

        val menuItems = menu.split("<br/>")
        for (menuItem in menuItems) {
            val nameAndAllergies = menuItem.split(" ")

            val foodName = menuItem.removeSuffix(" " + nameAndAllergies.last())

            var allergiesStr = nameAndAllergies.last().removePrefix("(")
            allergiesStr = allergiesStr.removeSuffix(")")

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