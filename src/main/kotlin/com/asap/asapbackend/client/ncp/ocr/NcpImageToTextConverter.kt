package com.asap.asapbackend.client.ncp.ocr

import com.asap.asapbackend.client.ncp.NcpApiProperties
import com.asap.asapbackend.client.ncp.ocr.dto.NcpOcrResponse
import com.asap.asapbackend.global.util.ImageToTextConverter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Component
class NcpImageToTextConverter(
    private val ncpApiProperties: NcpApiProperties
) : ImageToTextConverter {
    override fun convertImageToText(imageUrls: List<String>): String {
        val ncpOcrResponse = imageUrls
            .map { sendRequestToNcpOcrApi(it) }

        val text = Flux.fromIterable(ncpOcrResponse)
            .flatMap {
                it.map { response ->
                    response?.getInferText() ?: ""
                }.switchIfEmpty(Mono.just(""))
            }
            .blockLast()
        return text ?: ""
    }


    private fun sendRequestToNcpOcrApi(imageUrl: String): Mono<NcpOcrResponse?> {
        return WebClient.create(NCP_OCR_API_URL).post()
            .header("X-OCR-SECRET", ncpApiProperties.ocrKey)
            .header("Content-Type", "application/json")
            .bodyValue(
                mapOf(
                    "version" to "v2",
                    "requestId" to UUID.randomUUID(),
                    "timestamp" to 0,
                    "lang" to "ko",
                    "images" to listOf(
                        mapOf(
                            "format" to "png",
                            "name" to "string",
                            "url" to imageUrl
                        )
                    )
                )
            )
            .retrieve()
            .onStatus({ status -> status.is4xxClientError || status.is5xxServerError }) {
                it.bodyToMono(String::class.java)
                    .map { body -> RuntimeException("NCP OCR API Error: $body") }
            }
            .bodyToMono(NcpOcrResponse::class.java)
    }

    companion object {
        private val NCP_OCR_API_URL =
            "https://79vlzuxe20.apigw.ntruss.com/custom/v1/31007/86118a3b7fc92492b026562882076481e99119cc9563879b4f0076cf8d7bff31/general"
    }
}