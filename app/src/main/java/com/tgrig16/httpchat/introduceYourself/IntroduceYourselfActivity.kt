package com.tgrig16.httpchat.introduceYourself

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.tgrig16.httpchat.R

class IntroduceYourselfActivity : AppCompatActivity() {

    lateinit var presenter: IntroduceYourselfContract.Presenter
    lateinit var profileImage: ImageView
    lateinit var nickname: EditText
    lateinit var whatIDo: EditText
    lateinit var startButton: Button

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce_yourself)

        presenter = IntroduceYourselfPresenterImpl(this)
        profileImage = findViewById(R.id.profileImage)
        nickname = findViewById(R.id.nickname)
        whatIDo = findViewById(R.id.whatIDo)
        startButton = findViewById(R.id.startButton)

        profileImage.setOnClickListener {
            presenter.uploadImage()
        }

        startButton.setOnClickListener {
            presenter.clickStart(nickname.text.toString(), whatIDo.text.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            val imageUrl = data?.data
            profileImage.setImageURI(imageUrl)
        }
    }

}