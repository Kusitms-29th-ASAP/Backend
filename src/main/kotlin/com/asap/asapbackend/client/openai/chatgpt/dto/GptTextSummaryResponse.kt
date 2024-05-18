package com.asap.asapbackend.client.openai.chatgpt.dto

import com.fasterxml.jackson.databind.ObjectMapper

data class GptTextSummaryResponse(
    val choices: List<GptChoice>
) {
    fun getSummary(jsonConverter: ObjectMapper): List<String>{
        val summaryResponse = choices.map { it.content.replace("```json", "").replace("```","") }
        return jsonConverter.readValue(summaryResponse[0], SummaryResponse::class.java).summary.map { it.text }
    }
}

data class GptChoice(
    val message: GptMessage
){
    val content: String
        get() = message.content
}

data class GptMessage(
    val content: String
)

data class SummaryResponse(
    val summary: List<SummaryContent>
)

data class SummaryContent(
    val text: String
)