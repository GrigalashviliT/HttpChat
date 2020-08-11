package com.tgrig16.httpchat.services

import android.content.Context
import com.tgrig16.httpchat.database.DatabaseManager
import com.tgrig16.httpchat.database.entities.User

class UsersService(context: Context) {

    val database = DatabaseManager.getDatabase(context).getChatListDao()

    fun getUsers(): List<User> {
        return database.getAllUsers()
    }

}