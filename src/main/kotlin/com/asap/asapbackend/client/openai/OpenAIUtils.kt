package com.asap.asapbackend.client.openai

object OpenAIUtils {

    fun chatGptRequestFormat(prompt: String, requestMessage: String)= mapOf(
        "model" to "gpt-4o",
        "messages" to listOf(
            mapOf(
                "role" to "system",
                "content" to prompt
            ),
            mapOf(
                "role" to "user",
                "content" to requestMessage
            )
        )
    )

    const val GPT_API_URL = "https://api.openai.com/v1/chat/completions"
}