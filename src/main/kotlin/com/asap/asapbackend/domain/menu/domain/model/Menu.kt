package com.asap.asapbackend.domain.menu.domain.model

import jakarta.persistence.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("menu")
class Menu(
    @Id
    val id: String? = null,
    val schoolId: Long,
    val day: LocalDate,
    val food: List<Food>
) {

}