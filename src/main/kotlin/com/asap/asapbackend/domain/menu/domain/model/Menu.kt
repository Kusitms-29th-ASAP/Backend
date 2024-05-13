package com.asap.asapbackend.domain.menu.domain.model

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate

@Entity
class Menu(
    school: School,
    day: LocalDate,
    foods: List<Food>
) : BaseDateEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    val school: School = school

    val day: LocalDate = day

    @JdbcTypeCode(SqlTypes.JSON)
    val foods: List<Food> = foods
}