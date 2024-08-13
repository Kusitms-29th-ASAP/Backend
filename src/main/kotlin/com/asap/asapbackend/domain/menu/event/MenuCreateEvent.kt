package com.asap.asapbackend.domain.menu.event

import com.asap.asapbackend.domain.menu.domain.model.Menu

data class MultiMenuCreateEvent(
    val menus: Set<Menu>
) {
}