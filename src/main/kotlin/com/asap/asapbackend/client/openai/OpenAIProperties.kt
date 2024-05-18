package com.asap.asapbackend.client.openai

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "key.openai")
data class OpenAIProperties(
    val apiKey: String
) {
}