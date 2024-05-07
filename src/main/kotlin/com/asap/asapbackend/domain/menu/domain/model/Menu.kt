package com.asap.asapbackend.domain.menu.domain.model

import com.asap.asapbackend.domain.school.domain.model.School
import com.asap.asapbackend.global.domain.BaseDateEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class Menu(
    school: School,
    day: LocalDate
) : BaseDateEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    val school: School = school
}