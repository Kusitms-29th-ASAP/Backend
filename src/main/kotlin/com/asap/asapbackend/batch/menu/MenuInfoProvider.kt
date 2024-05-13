package com.asap.asapbackend.batch.menu

import com.asap.asapbackend.domain.school.domain.model.School
import java.time.LocalDate

interface MenuInfoProvider {
    fun retrieveMenuInfo(batchSize: Int, pageNumber: Int): MenuDataContainer

    data class MenuDataContainer(
        val menuInfo: List<MenuResponse>,
        val hasNext: Boolean
    )

    data class MenuResponse(
        val school: School,
        val menu: String,
        val day: LocalDate
    )
}