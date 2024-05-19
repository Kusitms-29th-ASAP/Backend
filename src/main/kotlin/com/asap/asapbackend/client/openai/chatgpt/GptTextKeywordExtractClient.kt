package com.asap.asapbackend.client.openai.chatgpt

import com.asap.asapbackend.client.openai.OpenAIProperties
import com.asap.asapbackend.client.openai.OpenAIUtils
import com.asap.asapbackend.client.openai.chatgpt.dto.GptResponse
import com.asap.asapbackend.client.openai.chatgpt.dto.KeywordResponse
import com.asap.asapbackend.global.util.TextKeywordExtractor
import com.asap.asapbackend.global.util.UtilException
import com.asap.asapbackend.global.util.jsonExtractor
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class GptTextKeywordExtractClient(
    private val objectMapper: ObjectMapper,
    private val openAIProperties: OpenAIProperties
) : TextKeywordExtractor {
    override fun extractKeywords(text: String): List<String> {
        return sendRequestGptToExtractKeywords(text).getKeywords()
    }

    private fun sendRequestGptToExtractKeywords(text: String): GptResponse {
        return WebClient.create(OpenAIUtils.GPT_API_URL)
            .post()
            .bodyValue(OpenAIUtils.chatGptRequestFormat(KEYWORD_EXTRACT_PROMPT, text))
            .header("Authorization", "Bearer ${openAIProperties.apiKey}")
            .header("Content-Type", "application/json")
            .retrieve()
            .onStatus({ status -> status.is4xxClientError || status.is5xxServerError }) {
                it.bodyToMono(String::class.java)
                    .map { body -> UtilException.TextSummarizationException(body) }
            }
            .bodyToMono(GptResponse::class.java)
            .block() ?: throw UtilException.TextSummarizationException()
    }

    private fun GptResponse.getKeywords(): List<String> {
        val keywordResponse = choices.map { it.content.jsonExtractor() }
        return objectMapper.readValue(keywordResponse[0], KeywordResponse::class.java).keywords.map { it.text }
    }

    companion object {
        private const val KEYWORD_EXTRACT_PROMPT = """
            위 문장에서 키워드를 추출해줘 추출할 키워드는 다음과 같은 형태로 출력해줘, 키워드를 추출할때는 딱 2개만 추출해줘
            위 문장은 초등학생들을 대상으로 배포된 가정통신문이고 해당 가정통신문에서 키워드를 추출한 결과는 부모님이 확인할거야 그 상황에 맞게 다음과 같은 형태로 키워드를 추출해줘
            다시 말하지만 키워드는 딱 2개만 추출해줘
            
            {
                "keywords" : [
                    { "text" : "String" },
                    { "text" : "String" }
                ]
            }
        """
    }

}