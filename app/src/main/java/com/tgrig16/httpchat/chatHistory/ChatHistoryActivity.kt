package com.tgrig16.httpchat.chatHistory

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.chat.ChatActivity

class ChatHistoryActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)

        // TODO chatHistory activity

        startActivity(Intent(applicationContext, ChatActivity::class.java))
    }

}