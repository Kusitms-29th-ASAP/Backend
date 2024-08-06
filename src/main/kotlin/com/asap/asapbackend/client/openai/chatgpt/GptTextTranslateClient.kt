package com.asap.asapbackend.client.openai.chatgpt

import com.asap.asapbackend.global.util.TextTranslator
import com.asap.asapbackend.global.vo.Language
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component

@Component
class GptTextTranslateClient(
    private val gptClient: GptClient,
    private val objectMapper: ObjectMapper
) : TextTranslator {
    override fun translate(text: String): TextTranslator.TranslateResponse{
        val policy = GptClient.Policy(
            GptTextTranslatePrompt.TRANSLATION_INSTRUCTION,
            GptTextTranslatePrompt.EXAMPLE_CONTENT,
            GptTextTranslatePrompt.TRANSLATION_RESPONSE,
            true
        )
        val result = gptClient.getResultForContentWithPolicy(text, policy) ?: run {
            throw IllegalStateException("Failed to get result from GPT")
        }

        return objectMapper.readValue(result)
    }


    private object GptTextTranslatePrompt {
        val TRANSLATION_INSTRUCTION = """
            위 문장을 ${Language.getAllLanguageNames()} 언어로 번역해줘, 변역을 완료하면 JSON 형태로 출력해줘.
            각 언어를 번역할때는 언어에 맞는 key와 value로 출력해줘. 예시 형태는 다음과 같아.
        """

        val TRANSLATIONS = Language.entries.joinToString(", ") { language ->
            """{ "language" : "${language.name}", "text" : "String" }"""
        }

        val TRANSLATION_RESPONSE = """
            {
                "translations" : [
                    $TRANSLATIONS
                ]
            }
        """

        val EXAMPLE_CONTENT = """
            {
                "translations" : [
                    { "language" : "KOREAN", "text" : "안녕하세요" },
                    { "language" : "ENGLISH", "text" : "Hello" },
                    { "language" : "CHINESE", "text" : "你好" },
                    { "language" : "JAPANESE", "text" : "こんにちは" },
                    { "language" : "VIETNAMESE", "text" : "Xin chào" },
                    { "language" : "PILIPINO", "text" : "Kamusta" }
                ]
            }
        """.trimIndent()


    }
}