package com.tgrig16.httpchat.chatHistory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.MessageItem
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.database.ChatHistoryItem

class ChatHistoryItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var data: List <ChatHistoryItem>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate( R.layout.chat_history_cell, parent, false)
            return ChatHistoryItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List <ChatHistoryItem> ){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as ChatHistoryItemViewHolder
        h.bind(data[position].personName, data[position].lastMessage , data[position].time )
    }

}