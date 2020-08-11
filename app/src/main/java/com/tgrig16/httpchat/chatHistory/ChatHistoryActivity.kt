package com.tgrig16.httpchat.chatHistory

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.chat.ChatActivity
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatDatabase
import kotlinx.android.synthetic.main.activity_chat_history.*

class ChatHistoryActivity : AppCompatActivity() , ChatHistoryContract.View ,
    ChatHistoryItemViewHolder.ChatHistoryListItemViewHolderDelegate {
    private lateinit var presenter: ChatHistoryContract.Presenter
     var userId: Long = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)
        userId = intent.getLongExtra("userId" , -1)
        presenter = ChatHistoryPresenterImpl(this )
        val viewManager = LinearLayoutManager(this)
        var recyclerView : RecyclerView = chats_recycler
        presenter.implementRecyclerView(viewManager , recyclerView)
    }

    override fun onResume() {
        super.onResume()
        presenter.viewDidLoad()
    }

    override fun getDataBase(): ChatDao {
        return Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase")
            .fallbackToDestructiveMigration().build().getChatListDao()
    }

    override fun getMessageFromEditText(): String {
        TODO("Not yet implemented")
    }

    override fun didTapChatItem(id: Long , personName: String) {
        val intent = Intent(applicationContext, ChatActivity::class.java)
        intent.putExtra("myId" , userId)
        intent.putExtra("secondId" , id)
        intent.putExtra("name" ,personName )
        startActivity(intent)
    }

}