package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Message
import com.tgrig16.httpchat.services.MessagesService
import org.json.JSONObject

class LastMessageHandler(var context: Context): RequestHandler {

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
                    val message = service.getLastMessage(firstUserId, secondUserId)

                    val response = JSONObject()
                    response.put("messages", Message(message.senderId, message.receiverId, message.time, message.text))
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }
}