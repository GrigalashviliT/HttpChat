package com.tgrig16.httpchat.introduceYourself

import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import com.tgrig16.httpchat.chatHistory.ChatHistoryActivity
import com.tgrig16.httpchat.entities.RegisterData
import com.tgrig16.httpchat.entities.RegisterStatus
import com.tgrig16.httpchat.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntroduceYourselfPresenterImpl(val view : IntroduceYourselfActivity) : IntroduceYourselfContract.Presenter {

    override fun uploadImage() {
        val gallery =  Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        view.startActivityForResult(gallery , 1)
    }

    override fun clickStart(imageB64: String, name: String, whatIDo: String) {
        if (name.isEmpty()) {
            Toast.makeText(view.applicationContext,"Nickname can't be empty", Toast.LENGTH_SHORT).show()
        } else if (whatIDo.isEmpty()) {
            Toast.makeText(view.applicationContext,"Please fill what I do", Toast.LENGTH_SHORT).show()
        } else {
            registerUser(RegisterData(imageB64, name, whatIDo))
        }
    }

    private val api by lazy {
        API.init()
    }

    private fun registerUser(data: RegisterData) {
        val call = api.registerUser(data)

        call.enqueue(object: Callback<RegisterStatus> {

            override fun onResponse(call: Call<RegisterStatus>, response: Response<RegisterStatus>) {
                val userId = response.body()?.id

                if (userId != null) {
                    view.startActivity(Intent(view.applicationContext, ChatHistoryActivity::class.java))
                } else {
                    Toast.makeText(view.applicationContext,"Couldn't register. Please try again", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterStatus>, t: Throwable) {
                Toast.makeText(view.applicationContext,"Couldn't register. Please try again", Toast.LENGTH_SHORT).show()
            }

        })
    }

}