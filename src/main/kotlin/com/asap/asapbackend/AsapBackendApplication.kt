package com.asap.asapbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class AsapBackendApplication

fun main(args: Array<String>) {
    runApplication<AsapBackendApplication>(*args)
}
