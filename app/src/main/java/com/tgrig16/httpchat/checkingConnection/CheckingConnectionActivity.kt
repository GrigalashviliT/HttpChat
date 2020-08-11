package com.tgrig16.httpchat.checkingConnection

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tgrig16.httpchat.R
import com.tgrig16.httpchat.httpServer.HTTPServerImpl
import com.tgrig16.httpchat.introduceYourself.IntroduceYourselfActivity

class CheckingConnectionActivity: AppCompatActivity(), CheckingConnectionContract.View {

    private lateinit var presenter: CheckingConnectionContract.Presenter

    private lateinit var checkingText: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_connection)

        startService(Intent(this, HTTPServerImpl::class.java))

        presenter = CheckingConnectionPresenterImpl(this)

        checkingText = findViewById(R.id.checkingText)
        progressBar = findViewById(R.id.progressBar)

        Handler().postDelayed(presenter::checkConnection, 3000)
    }

    override fun displaySuccess() {
        startActivity(Intent(applicationContext, IntroduceYourselfActivity::class.java))
    }

    override fun displayError() {
        progressBar.visibility = View.GONE
        checkingText.text = "Connection Failed"
    }

}