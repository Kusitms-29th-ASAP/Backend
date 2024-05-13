package com.asap.asapbackend.batch.menu

import com.asap.asapbackend.domain.menu.domain.model.Menu

interface MenuInfoProvider {
    fun retrieveMenuInfo(batchSize: Int, pageNumber: Int): MenuDataContainer

    data class MenuDataContainer(
        val menuInfo: List<Menu>,
        val hasNext: Boolean
    )
}