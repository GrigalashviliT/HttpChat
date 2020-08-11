package com.tgrig16.httpchat.chatHistory

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.database.MessageItem
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.database.ChatHistoryItem

class ChatHistoryItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private  var data =  ArrayList<ChatHistoryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate( R.layout.chat_history_cell, parent, false)
            return ChatHistoryItemViewHolder(view)
    }

    private lateinit var delegate: ChatHistoryItemViewHolder.ChatHistoryListItemViewHolderDelegate
    override fun getItemCount(): Int {

        return data.size
    }

    fun setDelegate(delegate: ChatHistoryActivity) {
        this.delegate = delegate
    }

    fun setData(data: List <ChatHistoryItem> ){
        this.data.clear()
        data.forEach{
            this.data.add(it)
        }

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as ChatHistoryItemViewHolder

        h.bind(data[position].personName, data[position].lastMessage , data[position].time , data[position].id , this.delegate )
    }

}