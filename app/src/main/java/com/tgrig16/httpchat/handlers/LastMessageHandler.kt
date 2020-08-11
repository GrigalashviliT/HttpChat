package com.tgrig16.httpchat.handlers

import android.content.Context
import android.util.Log
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Message
import com.tgrig16.httpchat.services.MessagesService
import org.json.JSONObject
import java.util.*

class LastMessageHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "POST") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)
                    val first: Int = jsonBody["first"] as Int
                    val second: Int = jsonBody["second"] as Int


                    val service = MessagesService(context)
                    val message = service.getLastMessage(first.toLong(), second.toLong())

                    val response = JSONObject()
                    response.put("senderId",message?.senderId ?: -1)
                    response.put("receiverId",message?.receiverId ?: -1)
                    response.put("time",message?.time )
                    response.put("text",message?.text ?: "")

                    sendResponse(exchange, response.toString())
                }
            }
        }
    }
}