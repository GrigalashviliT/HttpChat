package com.tgrig16.httpchat.checkingConnection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tgrig16.httpchat.introduceYourself.IntroduceYourselfActivity
import com.tgrig16.httpchat.R

class CheckingConnectionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_connection)

        // TODO check connection

        startActivity(Intent(applicationContext, IntroduceYourselfActivity::class.java))
    }

}