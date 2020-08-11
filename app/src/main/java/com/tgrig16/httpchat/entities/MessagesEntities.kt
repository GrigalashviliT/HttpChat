package com.tgrig16.httpchat.entities

import java.util.*

data class Messages(
    val messages: List<Message>
)

data class Message(
    val senderId: Long,
    val receiverId: Long,
    val time: Date,
    val text: String
)

data class MessageSentStatus(
    val success: Boolean
)