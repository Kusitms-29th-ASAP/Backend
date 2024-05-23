package com.asap.asapbackend.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class CorsConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://www.schoolpoint.site",
                "https://schoolpoint.site",
                "https://teacher.schoolpoint.site",
            )
            .allowedMethods("*")
            .allowedHeaders("*")
    }
}