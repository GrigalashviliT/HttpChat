package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Message
import com.tgrig16.httpchat.entities.Messages
import com.tgrig16.httpchat.services.MessagesService
import org.json.JSONObject

class MessagesHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "GET") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val firstUserId: Long = jsonBody["firstUserId"] as Long
                    val secondUserId: Long = jsonBody["secondUserId"] as Long

                    val service = MessagesService(context)
                    val messages = service.getMessages(firstUserId, secondUserId)

                    val result = mutableListOf<Message>()
                    messages.forEach { result.add(Message(it.senderId, it.receiverId, it.time, it.text)) }

                    val response = JSONObject()
                    response.put("messages", Messages(result.toList()))
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }

}