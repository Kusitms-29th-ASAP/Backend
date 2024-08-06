package com.asap.asapbackend.client.openai.chatgpt

import com.asap.asapbackend.client.openai.OpenAIProperties
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GptClient(
    private val openAIProperties: OpenAIProperties
) {

    fun getResultForContentWithPolicy(
        content: String,
        policy: Policy
    ): String? {
        val response = WebClient.builder()
            .baseUrl("https://api.openai.com")
            .build()
            .post()
            .uri("/v1/chat/completions")
            .header("Authorization", "Bearer ${openAIProperties.apiKey}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                mapOf(
                    "model" to "gpt-4o",
                    "messages" to listOf(
                        mapOf(
                            "role" to "system",
                            "content" to policy.instruction
                        ),
                        mapOf(
                            "role" to "user",
                            "content" to policy.exampleContent
                        ),
                        mapOf(
                            "role" to "assistant",
                            "content" to policy.exampleResult
                        ),
                        mapOf(
                            "role" to "user",
                            "content" to content
                        )
                    )
                )
            )
            .retrieve()
            .bodyToMono(GptResponse::class.java)
            .block()
        return response?.let {
            it.getMessage(0).also { message ->
                if (policy.isJson)
                    convertJsonFormat(message)
            }
        }
    }

    private fun convertJsonFormat(content: String): String {
        return content.replace("```json", "").replace("```", "")
    }

    data class Policy(
        val instruction: String,
        val exampleContent: String,
        val exampleResult: String,
        val isJson: Boolean
    )

    data class GptResponse(
        val id: String,
        val model: String,
        val choices: List<Choice>
    ) {
        fun getMessage(index: Int): String {
            return choices[index].message.content
        }
    }

    data class Choice(
        val index: Int,
        val message: Message
    )

    data class Message(
        val role: String,
        val content: String
    )
}