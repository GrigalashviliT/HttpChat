package com.tgrig16.httpchat.chat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.R

class ChatItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var mTitleView: TextView? = null
    private var timeText: TextView? = null

    init {
        mTitleView =  itemView.findViewById(R.id.my_message)
        timeText = itemView.findViewById(R.id.time)
    }

    fun bind(text: String, time: String) {
        mTitleView?.text   = text
        timeText?.text = time
    }

}