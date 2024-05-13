package com.asap.asapbackend.client.openapi.vo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix="seoul-open-api")
class SeoulOpenApiKey (
    val key: String
)