package com.asap.asapbackend.domain.menu.domain.repository

import com.asap.asapbackend.domain.menu.domain.model.Menu
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<Menu, Long> {
}