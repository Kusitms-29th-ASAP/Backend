package com.asap.asapbackend.client.openai.chatgpt

import com.asap.asapbackend.client.openai.OpenAIProperties
import com.asap.asapbackend.client.openai.OpenAIUtils
import com.asap.asapbackend.client.openai.chatgpt.dto.GptResponse
import com.asap.asapbackend.client.openai.chatgpt.dto.SummaryResponse
import com.asap.asapbackend.global.util.TextSummaryHelper
import com.asap.asapbackend.global.util.UtilException
import com.asap.asapbackend.global.util.jsonExtractor
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient


@Component
class GptTextSummaryClient(
    private val objectMapper: ObjectMapper,
    private val openAIProperties: OpenAIProperties
) : TextSummaryHelper {
    override fun summarizeText(text: String): List<String> {
        return sendRequestGptToSummarizeText(text).getSummary()
    }

    private fun sendRequestGptToSummarizeText(text: String): GptResponse {
        return WebClient.create(OpenAIUtils.GPT_API_URL)
            .post()
            .bodyValue(OpenAIUtils.chatGptRequestFormat(SUMMARY_PROMPT, text))
            .header("Authorization", "Bearer ${openAIProperties.apiKey}")
            .header("Content-Type", "application/json")
            .retrieve()
            .onStatus({ status -> status.is4xxClientError || status.is5xxServerError }) {
                it.bodyToMono(String::class.java)
                    .map { body -> UtilException.TextSummarizationException(body)}
            }
            .bodyToMono(GptResponse::class.java)
            .block() ?: throw UtilException.TextSummarizationException()
    }

    private fun GptResponse.getSummary(): List<String>{
        val summaryResponse = choices.map { it.content.jsonExtractor() }
        return objectMapper.readValue(summaryResponse[0], SummaryResponse::class.java).summary.map { it.text }
    }

    companion object {
        private const val SUMMARY_PROMPT = """
            위 문장을 3문장으로 요약해줘 요약할 문장은 각각 다음과 같은 형태로 출력해줘
            위 문장을 초등학생들을 대상으로 배포된 가정통신문이고 해당 가정통신문을 요약한 결과는 부모님이 확인할거야 그 상황에 맞게 다음과 같은 형태로 요약해줘

        {
            "summary" : [
                { "text" : "String" },
                { "text" : "String" },
                { "text" : "String" }
            ]
        }
        """
    }
}