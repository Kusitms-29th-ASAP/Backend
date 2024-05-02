package com.asap.asapbackend.domain.menu.domain.model

data class Food(
    val name: String,
    val allergies: List<Allergy>
) {
}