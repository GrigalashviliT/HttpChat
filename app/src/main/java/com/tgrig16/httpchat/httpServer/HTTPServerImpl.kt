package com.tgrig16.httpchat.httpServer

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import com.sun.net.httpserver.HttpServer
import com.tgrig16.httpchat.handlers.ConnectionCheckingHandler
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.*
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

            mHttpServer.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}