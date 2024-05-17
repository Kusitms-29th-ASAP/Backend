package com.asap.asapbackend.global.util

interface ImageToTextConverter {
    fun convertImageToText(imageUrls: List<String>): String
}