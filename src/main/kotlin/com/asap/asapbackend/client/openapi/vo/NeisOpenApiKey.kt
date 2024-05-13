package com.asap.asapbackend.client.openapi.vo

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix="neis-open-api")
class NeisOpenApiKey (
    val key: String
)