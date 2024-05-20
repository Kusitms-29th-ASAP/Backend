package com.asap.asapbackend.client.openai.chatgpt.dto

import com.asap.asapbackend.global.util.jsonExtractor
import com.fasterxml.jackson.databind.ObjectMapper


data class KeywordResponse(
    val keywords: List<KeywordContent>
)

data class KeywordContent(
    val text: String
)