package com.tgrig16.httpchat.WelcomePage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.room.Room
import com.tgrig16.httpchat.DATABASE.*
import com.tgrig16.httpchat.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.welcome_main.*

class WelcomeActivity : AppCompatActivity() {

    lateinit var presenter: WelcomeContract.Presenter

    @SuppressLint("CheckResult")
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
        Log.d("database will create", "not yet")
        var database: ChatDao =
            Room.databaseBuilder(this, ChatDatabase::class.java, "ChatDatabase").fallbackToDestructiveMigration().build()
                .getChatListDao()
        var l = MessageItem("სალამი", true, "20:20", 1)
        var n = MessageItem("გაგიმარჯოს", false, "20:20", 1)
        var ch = ChatItem(1 , "ვიღაცა")
        var lists = ArrayList<MessageItem>()
        lists.add(l)
        lists.add(n)
        AsyncTask
            .execute { database.addChatToList(lists)}
        database.addMessageToChat(ch).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( {
                it ->
            Log.d("message", "success")

        }, {err ->
            println(err.stackTrace)

        })
        Handler().postDelayed({
            database.loadChat(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ item ->
                    Log.d("message", item.chats[0].message)
                    Log.d("chatis zoma", item.chats[1].message)
//                    item.chats.forEach {
//                        Log.d("name", it.message)
//                    }
                    Log.d("message", item.item?.personName)

                }, {err ->
                    println(err.stackTrace)
                }
                )
        }, 1000)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            var imageUrl = data?.data
            profile_image.setImageURI(imageUrl)
        }
    }
}