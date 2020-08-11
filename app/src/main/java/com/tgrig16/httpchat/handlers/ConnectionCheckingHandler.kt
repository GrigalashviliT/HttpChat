package com.tgrig16.httpchat.handlers

import com.sun.net.httpserver.HttpHandler
import org.json.JSONObject

class ConnectionCheckingHandler: RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "GET") {
                    val response = JSONObject()
                    response.put("success", true)

                    sendResponse(exchange, response.toString())
                }
            }
        }
    }

}