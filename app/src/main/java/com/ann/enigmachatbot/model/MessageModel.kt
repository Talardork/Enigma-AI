package com.ann.enigmachatbot.model

data class MessageModel(
    var isUser : Boolean, // to differ between the user and enigma
    var isImage : Boolean,//to differ b/w chat and image
    var message : String
)
