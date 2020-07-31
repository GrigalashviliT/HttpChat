package com.tgrig16.httpchat.WelcomePage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.room.Room
import com.tgrig16.httpchat.DATABASE.ChatDao
import com.tgrig16.httpchat.DATABASE.ChatDatabase
import com.tgrig16.httpchat.DATABASE.MessageItem
import com.tgrig16.httpchat.HttpServer.HTTPServerContract
import com.tgrig16.httpchat.R
import kotlinx.android.synthetic.main.welcome_main.*

class WelcomeActivity : AppCompatActivity()  {

    lateinit var presenter: WelcomeContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_main)
        presenter = WelcomePresenterImpl(this)
        profile_image.setOnClickListener {
            presenter.uploadImage()
        }

       whatToDo.doAfterTextChanged {
           presenter.changedWhatIDoText(it.toString())
       }

        personName.doAfterTextChanged {
            presenter.changedNameText(it.toString())
        }

        startButton.setOnClickListener {
            presenter.clickStart()
        }
         var database: ChatDao = Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase").build()
             .getChatListDao()
        var l = MessageItem("gamarjoba aba" , true , "20:20")
        var n = MessageItem("gagimarjos" , false , "20:20")
        database.addMessageToChat(l)
        database.addMessageToChat(n)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            var imageUrl = data?.data
            profile_image.setImageURI(imageUrl)
        }
    }
}