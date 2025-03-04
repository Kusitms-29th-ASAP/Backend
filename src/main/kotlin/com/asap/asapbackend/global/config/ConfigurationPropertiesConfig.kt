package com.asap.asapbackend.global.config

import com.asap.asapbackend.client.ncp.NcpApiProperties
import com.asap.asapbackend.client.openai.OpenAIProperties
import com.asap.asapbackend.client.openapi.vo.NeisOpenApiKey
import com.asap.asapbackend.client.openapi.vo.SeoulOpenApiKey
import com.asap.asapbackend.global.jwt.vo.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(
    JwtProperties::class,
    NeisOpenApiKey::class,
    SeoulOpenApiKey::class,
    OpenAIProperties::class,
    NcpApiProperties::class
)
class ConfigurationPropertiesConfig
