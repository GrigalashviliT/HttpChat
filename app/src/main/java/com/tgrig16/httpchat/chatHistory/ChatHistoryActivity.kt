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
import com.tgrig16.httpchat.chat.ChatContract
import com.tgrig16.httpchat.chat.ChatPresenterImpl
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatDatabase
import kotlinx.android.synthetic.main.activity_chat_history.*

class ChatHistoryActivity: AppCompatActivity() , ChatHistoryContract.View{
    private lateinit var presenter: ChatHistoryContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_history)
        presenter = ChatHistoryPresenterImpl(this)
        var recyclerView : RecyclerView = chats_recycler



        val viewManager = LinearLayoutManager(this)
        presenter.implementRecyclerView(viewManager , recyclerView)
        presenter.viewDidLoad("ვიღაცა")
        startActivity(Intent(applicationContext, ChatActivity::class.java))
        recyclerView.setOnTouchListener(object: OnSwipeTouchListener(this@ChatHistoryActivity) {
            override fun onSwipeLeft() {
                Log.d("ds" , "sfj")
                onBackPressed()
            }
            override fun onSwipeRight() {
                Log.d("ds" , "sfj")
                onBackPressed()
            }
        })
    }

    override fun getDataBase(): ChatDao {
        return Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase")
            .fallbackToDestructiveMigration().build().getChatListDao()
    }

    override fun getMessageFromEditText(): String {
        TODO("Not yet implemented")
    }

}