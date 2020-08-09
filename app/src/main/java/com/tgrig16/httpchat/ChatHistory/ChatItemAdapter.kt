package com.tgrig16.httpchat.ChatHistory

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.DATABASE.MessageItem
import com.tgrig16.httpchat.R


class ChatItemAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var data: List <MessageItem>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            val view: View = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.message_cell,
                    parent,
                    false
                )
            return ChatItemViewHolder(view)
        }
        val view: View = LayoutInflater.from(parent.getContext())
            .inflate( R.layout.message_cell_right,
                parent,
                false
            )
        return ChatItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position].isMyMessage ) {
            return 1
        }
        return 0
    }
    override fun getItemCount(): Int {
        Log.d("recycle" , data.size.toString())
        if (data == null) {
            return 0
        }
        return data.size
    }

    fun setData(data: List <MessageItem> ){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val h = holder as ChatItemViewHolder
        h.bind(data[position].message , data[position].time)
    }
}