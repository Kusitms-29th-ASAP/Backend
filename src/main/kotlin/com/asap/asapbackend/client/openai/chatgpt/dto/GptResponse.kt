package com.asap.asapbackend.client.openai.chatgpt.dto

data class GptResponse(
    val choices: List<GptChoice>
) {
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