package com.asap.asapbackend.client.ncp.ocr.dto

data class NcpOcrResponse(
    val version: String,
    val requestId: String,
    val timestamp: Long,
    val images: List<NcpOcrImage>
) {
    fun getInferText(): String{
        return images.flatMap { it.fields }
            .map { it.inferText }
            .joinToString(" ")
    }
}

data class NcpOcrImage(
    val fields: List<NcpOcrInferResult>
)


data class NcpOcrInferResult(
    val inferText: String
)