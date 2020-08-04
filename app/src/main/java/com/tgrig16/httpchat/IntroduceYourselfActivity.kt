package com.tgrig16.httpchat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IntroduceYourselfActivity: AppCompatActivity() {

    private var PICK_IMAGE = 1

    private lateinit var image: ImageView
    private lateinit var nickname: EditText
    private lateinit var whatIDo: EditText
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduce_yourself)

        image = findViewById(R.id.image)
        nickname = findViewById(R.id.nickname)
        whatIDo = findViewById(R.id.whatIDo)
        startButton = findViewById(R.id.start)

        image.setOnClickListener { onImageClick() }
        startButton.setOnClickListener { onStartButtonClick() }
    }

    private fun onImageClick() {
        val getIntent = Intent(Intent.ACTION_GET_CONTENT)
        getIntent.type = "image/*"

        val pickIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickIntent.type = "image/*"

        val chooserIntent = Intent.createChooser(getIntent, "Select Image")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))

        startActivityForResult(chooserIntent, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data!!.data!!
            image.setImageURI(imageUri)
        }
    }

    private fun onStartButtonClick() {
        val nickname = nickname.text.toString()
        val work = whatIDo.text.toString()

        if (nickname == "") {
            Toast.makeText(applicationContext,"Nickname can't be empty", Toast.LENGTH_SHORT).show()
        } else if (work == "") {
            Toast.makeText(applicationContext,"Please fill what I do", Toast.LENGTH_SHORT).show()
        } else {
            //register user
        }
    }

}