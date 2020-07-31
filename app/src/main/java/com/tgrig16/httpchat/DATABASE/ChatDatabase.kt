package com.tgrig16.httpchat.DATABASE

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MessageItem::class, ChatItem::class],
    version = 1)
abstract class ChatDatabase : RoomDatabase() {
    abstract fun getChatListDao(): ChatDao
}