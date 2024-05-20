package com.asap.asapbackend.domain.child.application.dto

import com.asap.asapbackend.domain.menu.domain.model.Allergy
import java.time.LocalDate

class ChangeChildInfo {
    data class Request (
        val childName: String,
        val birthday: LocalDate,
        val allergies: List<Allergy>
    )
}