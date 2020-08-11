package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Message
import com.tgrig16.httpchat.entities.Messages
import com.tgrig16.httpchat.services.MessagesService
import org.json.JSONArray
import org.json.JSONObject

class MessagesHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "POST") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val firstUserId: Int = jsonBody["first"] as Int
                    val secondUserId: Int = jsonBody["second"] as Int

                    val service = MessagesService(context)
                    val messages = service.getMessages(firstUserId.toLong(), secondUserId.toLong())

                    val messageJson = JSONArray()

                    messages.forEach { message ->
                        var obj = JSONObject()
                        obj.put("senderId" , message.senderId)
                        obj.put("receiveId" , message.receiverId)
                        obj.put("text" , message.text)
                        obj.put("time" , message.time)
                        messageJson.put(obj) }
                    val response = JSONObject()

                    response.put("messages", messageJson)
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }

}