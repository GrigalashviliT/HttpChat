package com.tgrig16.httpchat.chatHistory

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.ChatDao

interface ChatHistoryContract {

    interface View {
        fun getDataBase(): ChatDao
        fun getMessageFromEditText(): String
    }

    interface Presenter {
        fun implementRecyclerView(viewManager: LinearLayoutManager , recyclerView: RecyclerView)
        fun viewDidLoad()
        fun clickedSend()
        fun lazyLoader()
        fun fetchUsers()
    }
}