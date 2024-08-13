package com.asap.asapbackend.domain.menu.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

class Menu(
    id: Long = 0L,
    schoolId: Long,
    day: LocalDate,
    foods: List<Food>,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
){
    val id: Long = id

    val schoolId: Long = schoolId

    val day: LocalDate = day

    val foods: List<Food> = foods

    val createdAt: LocalDateTime = createdAt
    val updatedAt: LocalDateTime = updatedAt
}