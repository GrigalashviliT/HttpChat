package com.tgrig16.httpchat.chat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.amitshekhar.model.Response
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatDatabase
import java.nio.charset.Charset


class ChatActivity: ChatContract.View, AppCompatActivity() {

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView = findViewById(R.id.chatRecycler)

        val viewManager = LinearLayoutManager(this)
        presenter = ChatPresenterImpl(this)
        presenter.implementRecyclerView(viewManager , recyclerView)
        presenter.viewDidLoad("ვიღაცა")

        messageBox = findViewById(R.id.messageBox)

        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            presenter.clickedSend()
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(-10)) {
                    presenter.lazyLoader()
                }
            }
        })
    }


    override fun getDataBase() : ChatDao{
        return Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase")
            .fallbackToDestructiveMigration().build().getChatListDao()
    }

    override fun getMessageFromEditText(): String {
        return messageBox.text.toString()
    }

}