package com.tgrig16.httpchat.introduceYourself

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import com.tgrig16.httpchat.chatHistory.ChatHistoryActivity

class IntroduceYourselfPresenterImpl(val view : IntroduceYourselfActivity) : IntroduceYourselfContract.Presenter {

    override fun uploadImage() {
        val gallery =  Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        view.startActivityForResult(gallery , 1)
    }

    override fun clickStart(name: String, whatIDo: String) {
        if (name.isEmpty()) {
            Toast.makeText(view.applicationContext,"Nickname can't be empty", Toast.LENGTH_SHORT).show()
        } else if (whatIDo.isEmpty()) {
            Toast.makeText(view.applicationContext,"Please fill what I do", Toast.LENGTH_SHORT).show()
        } else {
            // TODO register user

            view.startActivity(Intent(view.applicationContext, ChatHistoryActivity::class.java))
        }
    }

}