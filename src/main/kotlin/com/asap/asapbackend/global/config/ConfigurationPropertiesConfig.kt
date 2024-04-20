package com.asap.asapbackend.global.config

import com.asap.asapbackend.global.security.jwt.JwtProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class ConfigurationPropertiesConfig
