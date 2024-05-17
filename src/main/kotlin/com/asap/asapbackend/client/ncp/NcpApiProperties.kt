package com.asap.asapbackend.client.ncp

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "key.ncp")
data class NcpApiProperties(
    val ocrKey: String
)