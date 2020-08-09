package com.tgrig16.httpchat.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatItem::class, MessageItem::class], version = 5)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun getChatListDao(): ChatDao
}