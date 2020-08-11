package com.tgrig16.httpchat.httpServer

import com.sun.net.httpserver.HttpServer
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.tgrig16.httpchat.handlers.*
import java.io.IOException
import java.net.InetSocketAddress
import java.util.concurrent.Executors

class HTTPServerImpl: Service() {

    private lateinit var mHttpServer: HttpServer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startServer(5500)
        return START_STICKY
    }

    private fun startServer(port: Int) {
        try {
            mHttpServer = HttpServer.create(InetSocketAddress(port), 0)
            mHttpServer.executor = Executors.newCachedThreadPool()

            mHttpServer.createContext("/check-connection", ConnectionCheckingHandler().getHandler())
            mHttpServer.createContext("/register", RegisterUserHandler(applicationContext).getHandler())
            mHttpServer.createContext("/users", RegisterUserHandler(applicationContext).getHandler())
            mHttpServer.createContext("/contacts", ContactsHandler(applicationContext).getHandler())
            mHttpServer.createContext("/send-message", MessageSentHandler(applicationContext).getHandler())
            mHttpServer.createContext("/messages", MessagesHandler(applicationContext).getHandler())
            mHttpServer.createContext("/last-message", LastMessageHandler(applicationContext).getHandler())

            mHttpServer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}