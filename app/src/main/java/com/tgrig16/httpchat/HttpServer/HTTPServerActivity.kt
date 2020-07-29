package com.tgrig16.httpchat.HttpServer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tgrig16.httpchat.R
import kotlinx.android.synthetic.main.activity_main.*


class HTTPServerActivity : AppCompatActivity() , HTTPServerContract.View {

    private var serverUp = false
    lateinit var presenter: HTTPServerContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val port = 5500
        presenter = HTTPServerImpl(this)
        serverButton.setOnClickListener {
            serverUp = if(!serverUp){
                presenter.startServer(port)
                true
            } else{
                presenter.stopServer()
                false
            }

        }

    }

    override fun setTextView(text: String) {
        serverTextView.text = text
    }

    override fun setButtonText(text: String) {
        serverButton.text = text
    }
}