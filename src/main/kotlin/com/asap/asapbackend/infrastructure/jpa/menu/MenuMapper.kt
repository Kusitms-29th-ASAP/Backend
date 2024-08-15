package com.asap.asapbackend.infrastructure.jpa.menu

import com.asap.asapbackend.domain.menu.domain.model.Food
import com.asap.asapbackend.domain.menu.domain.model.Menu
import com.asap.asapbackend.infrastructure.jpa.menu.entity.FoodEntity
import com.asap.asapbackend.infrastructure.jpa.menu.entity.MenuEntity


object MenuMapper {
    fun toModel(menuEntity: MenuEntity): Menu {
        return Menu(
            id = menuEntity.id,
            schoolId = menuEntity.schoolId,
            day = menuEntity.day,
            foods = toFoodModel(menuEntity.foods),
            createdAt = menuEntity.createdAt,
            updatedAt = menuEntity.updatedAt
        )
    }

    private fun toFoodModel(foodEntities: List<FoodEntity>): List<Food>{
        return foodEntities.map {
            Food(
                name = it.name,
                allergies = it.allergies
            )
        }
    }

    fun toEntity(menu: Menu): MenuEntity {
        return MenuEntity(
            id = menu.id,
            schoolId = menu.schoolId,
            day = menu.day,
            foods = toFoodEntity(menu.foods),
            createdAt = menu.createdAt,
            updatedAt = menu.updatedAt
        )
    }

    private fun toFoodEntity(foods: List<Food>): List<FoodEntity>{
        return foods.map {
            FoodEntity(
                name = it.name,
                allergies = it.allergies
            )
        }
    }
}