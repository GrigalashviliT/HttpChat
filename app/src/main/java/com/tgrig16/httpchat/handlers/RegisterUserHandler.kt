package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.services.RegisterService
import org.json.JSONObject

class RegisterUserHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "POST") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val imageB64: String = jsonBody["imageB64"] as String
                    val nickname: String = jsonBody["nickname"] as String
                    val whatIDo: String = jsonBody["whatIDo"] as String

                    val registerService = RegisterService(context)

                    val id = if (registerService.userExists(nickname)) {
                        registerService.getUser(nickname)
                    } else {
                        registerService.register(imageB64, nickname, whatIDo)
                    }

                    val response = JSONObject()
                    response.put("id", id)
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }
}