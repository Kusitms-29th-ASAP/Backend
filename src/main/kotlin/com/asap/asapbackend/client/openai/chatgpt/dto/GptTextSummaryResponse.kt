package com.asap.asapbackend.client.openai.chatgpt.dto



data class SummaryResponse(
    val summary: List<SummaryContent>
)

data class SummaryContent(
    val text: String
)