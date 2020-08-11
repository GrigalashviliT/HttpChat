package com.tgrig16.httpchat.services

import android.content.Context
import com.tgrig16.httpchat.database.DatabaseManager
import com.tgrig16.httpchat.database.entities.Message

class MessagesService(context: Context) {

    val database = DatabaseManager.getDatabase(context).getChatListDao()

    fun sendMessage(message: Message) {
        database.addMessage(Message(message.senderId, message.receiverId, message.time, message.text))
    }

    fun getMessages(firstUserId: Long, secondUserId: Long): List<Message> {
        return database.getMessages(firstUserId, secondUserId)
    }

    fun getLastMessage(firstUserId: Long, secondUserId: Long): Message? {
        return database.getLastMessageBetween(firstUserId, secondUserId)
    }

}