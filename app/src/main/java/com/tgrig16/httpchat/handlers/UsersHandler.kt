package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.User
import com.tgrig16.httpchat.entities.Users
import com.tgrig16.httpchat.services.UsersService
import org.json.JSONObject

class UsersHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "GET") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val service = UsersService(context)
                    val users = service.getUsers()

                    val result = mutableListOf<User>()
                    users.forEach { result.add(User(it.imageB64, it.nickname, it.whatIDo)) }

                    val response = JSONObject()
                    response.put("users", Users(result.toList()))
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }

}