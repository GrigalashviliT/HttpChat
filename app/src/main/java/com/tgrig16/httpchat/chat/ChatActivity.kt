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
import kotlinx.android.synthetic.main.activity_chat.*
import java.nio.charset.Charset


class ChatActivity: ChatContract.View, AppCompatActivity() {

    private lateinit var presenter: ChatContract.Presenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val myId = intent.getLongExtra("myId" , -1)
        val secondId = intent.getLongExtra("secondId" , -1)
        val personName = intent.getStringExtra("name")
        person.text = personName
        Log.e("myId" , myId.toString())
        Log.e("secondId" , secondId.toString())

        recyclerView = findViewById(R.id.chatRecycler)

        val viewManager = LinearLayoutManager(this)
        presenter = ChatPresenterImpl(this)
        presenter.implementRecyclerView(viewManager , recyclerView)
        presenter.viewDidLoad(myId , secondId)

        messageBox = findViewById(R.id.messageBox)

        sendButton = findViewById(R.id.sendButton)

        sendButton.setOnClickListener {
            presenter.clickedSend(messageBox.text.toString() , myId , secondId)
            messageBox.text.clear()
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(0)) {
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