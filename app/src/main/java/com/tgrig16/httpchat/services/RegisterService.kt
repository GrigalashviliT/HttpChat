package com.tgrig16.httpchat.services

import android.content.Context
import androidx.room.Room
import com.tgrig16.httpchat.database.ChatDatabase
import com.tgrig16.httpchat.database.entities.User

class RegisterService(val context: Context) {

    val database = Room.databaseBuilder(context, ChatDatabase::class.java, "ChatDatabase")
        .fallbackToDestructiveMigration().build().getChatListDao()

    fun getUser(nickname: String): Long? {
        return database.getUserByNickname(nickname)
    }

    fun userExists(nickname: String): Boolean {
        return getUser(nickname) != null
    }

    fun register(imageB64: String, nickname: String, whatIDo: String): Long {
        val user = User(imageB64, nickname, whatIDo)
        return database.registerUser(user)
    }

}