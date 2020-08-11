package com.tgrig16.httpchat.chat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.ChatDao

interface ChatContract {

    interface View {
        fun getDataBase(): ChatDao
        fun getMessageFromEditText(): String
    }

    interface Presenter {
        fun implementRecyclerView(viewManager: LinearLayoutManager , recyclerView: RecyclerView)
        fun viewDidLoad(firstUser: Long , secondUser: Long)
        fun clickedSend(message: String , myId : Long , yourId: Long)
        fun lazyLoader()
    }

}