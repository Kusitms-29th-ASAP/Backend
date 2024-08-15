package com.asap.asapbackend.batch.menu

import com.asap.asapbackend.domain.menu.domain.service.MenuAppender
import com.asap.asapbackend.global.util.TransactionUtils
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}


@Component
class MenuScheduler (
    private val menuInfoProvider: MenuInfoProvider,
    private val menuAppender: MenuAppender
){
    @Scheduled(cron = "0 30 5 1 * *") //매월 1일 새벽 5시 30분
    fun addMenu() {
        val batchSize = 100
        var pageNumber = 0

        val startTime = System.currentTimeMillis()
        do{
            val menuDataContainer = menuInfoProvider.retrieveMenuInfo(batchSize, pageNumber)

            pageNumber++

            TransactionUtils.writable {
                menuAppender.addMenus(menuDataContainer.menuInfo)
            }
        }while (menuDataContainer.hasNext)
        val endTime = System.currentTimeMillis()
        logger.info { "addMenu end, elapsed time: ${endTime - startTime}" }
    }
}