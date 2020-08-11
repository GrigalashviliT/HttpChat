package com.tgrig16.httpchat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tgrig16.httpchat.database.entities.User

@Database(entities = [ChatItem::class, MessageItem::class, User::class], version = 7)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun getChatListDao(): ChatDao
}