package com.ann.enigmachatbot.model.request

data class ImageGenerationRequest(
    val n: Int,
    val prompt: String,
    val size: String
)