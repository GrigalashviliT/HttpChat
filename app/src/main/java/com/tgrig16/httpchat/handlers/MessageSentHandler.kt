package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Message
import com.tgrig16.httpchat.services.MessagesService
import org.json.JSONObject

class MessageSentHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "POST") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val message: Message = jsonBody["message"] as Message

                    val service = MessagesService(context)
                    service.sendMessage(com.tgrig16.httpchat.database.entities.Message(message.senderId, message.receiverId, message.time, message.text))

                    val response = JSONObject()
                    response.put("success", true)
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }
}