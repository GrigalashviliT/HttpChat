package com.tgrig16.httpchat.httpServer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.database.ChatDao
import com.tgrig16.httpchat.database.ChatDatabase

class HTTPServerActivity : AppCompatActivity() , HTTPServerContract.View {

    private val port = 5500
    private var serverUp = false
    private lateinit var presenter: HTTPServerContract.Presenter
    private lateinit var startButton: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_http_server)
//
//        presenter = HTTPServerImpl()

        startButton = findViewById(R.id.serverButton)
        startButton.setOnClickListener {
            serverUp = if (!serverUp) {
                presenter.startServer(port)
                true
            } else {
                presenter.stopServer()
                false
            }
        }

        textView = findViewById(R.id.serverTextView)
    }

    override fun createDataBase(): ChatDao {
        return Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase")
            .fallbackToDestructiveMigration().build().getChatListDao()
    }

    override fun setTextView(text: String) {
        textView.text = text
    }

    override fun setButtonText(text: String) {
        startButton.text = text
    }

}