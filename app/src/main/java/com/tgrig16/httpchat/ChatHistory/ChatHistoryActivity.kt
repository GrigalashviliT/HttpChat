package com.tgrig16.httpchat.ChatHistory

import android.os.Bundle
import android.text.Editable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.tgrig16.httpchat.DATABASE.ChatDao
import com.tgrig16.httpchat.DATABASE.ChatDatabase
import com.tgrig16.httpchat.R
import kotlinx.android.synthetic.main.chat_history_main.*


class ChatHistoryActivity : ChatHistoryContract.View  , AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    lateinit var presenter: ChatHistoryContract.Presenter

    override fun getDataBase() : ChatDao{
        var database: ChatDao =
            Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase").fallbackToDestructiveMigration().build()
                .getChatListDao()

        return database
    }

    override fun getMessageFromEditText(): String {
        return editTextPersonName.text.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_history_main)
        presenter = ChatHistoryPresenterImpl(this)
        recyclerView = chat_recycler
        var viewManager = LinearLayoutManager(this)

        presenter.implementRecyclerView(viewManager , recyclerView)
        presenter.viewDidLoad("ვიღაცა")
        send_button.setOnClickListener {
            presenter.clickedSend()
        }

    }

}