package com.tgrig16.httpchat.database


data class ChatHistoryItem(
    var personName: String,
    var lastMessage: String,
    var time: String
)