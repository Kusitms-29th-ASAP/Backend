package com.asap.asapbackend.global.util

fun String.jsonExtractor(): String{
    return this.replace("```json", "").replace("```","")
}