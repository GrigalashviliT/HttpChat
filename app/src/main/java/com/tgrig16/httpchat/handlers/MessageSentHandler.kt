package com.tgrig16.httpchat.handlers

import android.content.Context
import android.util.Log
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Message
import com.tgrig16.httpchat.services.MessagesService
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MessageSentHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "POST") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val senderId : Int = jsonBody["senderId"] as Int
                    val receiverId : Int = jsonBody["receiverId"] as Int
                    val time : String = jsonBody["time"] as String
                    val text : String = jsonBody["text"] as String
                    Log.e("time" , time )
                    val service = MessagesService(context)
                    service.sendMessage(com.tgrig16.httpchat.database.entities.Message(senderId.toLong(), receiverId.toLong(),
                        time, text))

                    val response = JSONObject()
                    response.put("success", true)
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }
}