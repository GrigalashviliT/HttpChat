package com.tgrig16.httpchat.handlers

import android.content.Context
import android.util.Log
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.User
import com.tgrig16.httpchat.entities.Users
import com.tgrig16.httpchat.services.UsersService
import org.json.JSONArray
import org.json.JSONObject

class UsersHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "GET") {
                    val service = UsersService(context)
                    val users = service.getUsers()

                    val usersJson = JSONArray()

                    users.forEach { user ->
                        var obj = JSONObject()
                        obj.put("imageB64" , user.imageB64)
                        obj.put("whatIDo" , user.whatIDo)
                        obj.put("nickname" , user.nickname)
                        obj.put("id" , user.id)
                        usersJson.put(obj) }
                    val response = JSONObject()

                    response.put("users", usersJson)
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }

}