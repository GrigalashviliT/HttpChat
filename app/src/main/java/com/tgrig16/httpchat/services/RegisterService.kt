package com.tgrig16.httpchat.services

import android.content.Context
import com.tgrig16.httpchat.database.DatabaseManager
import com.tgrig16.httpchat.database.entities.User

class RegisterService(context: Context) {

    val database = DatabaseManager.getDatabase(context).getChatListDao()

    fun getAllUsers(): List<User> {
        return database.getAllUsers()
    }

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