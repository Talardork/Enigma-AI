package com.ann.enigmachatbot.model.chatresponse

data class Choice(
    val text: String, //this is our response.
    val finish_reason: String,
    val index: Int,
    val logprobs: Any

)