package com.asap.asapbackend.infrastructure.jpa.menu.entity

import com.asap.asapbackend.domain.menu.domain.model.Allergy


data class FoodEntity(
    val name: String,
    val allergies: List<Allergy>
) {
}