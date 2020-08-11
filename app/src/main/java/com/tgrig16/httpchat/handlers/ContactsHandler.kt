package com.tgrig16.httpchat.handlers

import android.content.Context
import com.sun.net.httpserver.HttpHandler
import com.tgrig16.httpchat.entities.Contact
import com.tgrig16.httpchat.services.ContactsService
import org.json.JSONObject

class ContactsHandler(var context: Context): RequestHandler {

    override fun getHandler(): HttpHandler {
        return HttpHandler { exchange ->
            run {
                if (exchange!!.requestMethod == "GET") {
                    val inputStream = exchange.requestBody
                    val requestBody = streamToString(inputStream)
                    val jsonBody = JSONObject(requestBody)

                    val userId: Long = jsonBody["userId"] as Long

                    val service = ContactsService(context)
                    val contacts = service.getUserContacts(userId)

                    val result = mutableListOf<Contact>()
                    contacts.forEach { result.add(Contact(it)) }

                    val response = JSONObject()
                    response.put("contacts", result.toList())
                    sendResponse(exchange, response.toString())
                }
            }
        }
    }

}