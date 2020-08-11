package com.tgrig16.httpchat.chatHistory

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tgrig16.httpchat.R
import org.w3c.dom.Text

class ChatHistoryItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private var mTitleView: TextView? = null
    private var timetextHistory: TextView? = null
    private var timeText: TextView? = null
    private lateinit var delegate: ChatHistoryListItemViewHolderDelegate
    interface ChatHistoryListItemViewHolderDelegate {
        fun didTapChatItem(id: Long , personName : String )
    }

    init {
        mTitleView =  itemView.findViewById(R.id.ChatHistoryName)
        timeText = itemView.findViewById(R.id.LastMessages)
        timetextHistory = itemView.findViewById(R.id.timetextHistory)

    }

    fun bind(name: String, lastMsg: String , timeStr: String , id: Long , delegate: ChatHistoryListItemViewHolderDelegate ) {
        mTitleView?.text   = name
        timeText?.text = lastMsg
        timetextHistory?.text = timeStr
        itemView.setOnClickListener {
            delegate.didTapChatItem(id , name )
        }
    }


}