package com.tgrig16.httpchat.HttpServer

interface HTTPServerContract {
    interface View {
        fun setTextView(text: String)
        fun setButtonText(text: String)
    }

    interface Presenter {
        fun startServer(port: Int)
        fun stopServer()
    }
}