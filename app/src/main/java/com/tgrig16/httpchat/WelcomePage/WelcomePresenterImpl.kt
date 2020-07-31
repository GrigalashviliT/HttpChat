package com.tgrig16.httpchat.WelcomePage

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.tgrig16.httpchat.HttpServer.HTTPServerActivity
import kotlinx.android.synthetic.main.welcome_main.*

class WelcomePresenterImpl(val view : WelcomeActivity) : WelcomeContract.Presenter {
    private var whatIDo: String? = null
    private var userName: String? = null
    private var isValidFirst = false
    private var isValidSecond = false

    fun lengthIsZero(text: String): Boolean {
        return text.length == 0
    }

    override fun changedWhatIDoText(text: String) {
        whatIDo = text
        isValidFirst = !lengthIsZero(text)
    }

    override fun changedNameText(text: String) {
        userName = text
        isValidSecond = !lengthIsZero(text)
    }

    override fun uploadImage() {
        var gallery =  Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        view.startActivityForResult(gallery , 1)
    }

    override fun clickStart() {
        if (isValidFirst && isValidSecond ){
            Log.d("isValid" , "true")

        }else{
            Log.d("isValid" , "false")
        }
    }
}