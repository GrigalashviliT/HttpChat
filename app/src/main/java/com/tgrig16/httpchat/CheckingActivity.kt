package com.tgrig16.httpchat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class CheckingActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking)

        //check for connectivity

        startActivity(Intent(applicationContext, IntroduceYourselfActivity::class.java))
    }

}