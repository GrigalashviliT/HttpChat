package com.tgrig16.httpchat.httpServer

import com.tgrig16.httpchat.database.ChatDao

interface HTTPServerContract {

    interface View {
        fun setTextView(text: String)
        fun setButtonText(text: String)
        fun createDataBase(): ChatDao
    }

    interface Presenter {
        fun startServer(port: Int)
        fun stopServer()
    }

}