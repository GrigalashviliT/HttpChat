package com.tgrig16.httpchat.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tgrig16.httpchat.database.entities.Contact
import com.tgrig16.httpchat.database.entities.Message
import com.tgrig16.httpchat.database.entities.User

@Database(entities = [User::class, Contact::class, Message::class], version = 8)
@TypeConverters(value = [Converters::class])
abstract class ChatDatabase: RoomDatabase() {
    abstract fun getChatListDao(): ChatDao
}

class DatabaseManager {

    companion object {
        private var database: ChatDatabase? = null

        fun getDatabase(context: Context): ChatDatabase {
            if (database == null) {
                database = Room.databaseBuilder(context, ChatDatabase::class.java, "messaging_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return database as ChatDatabase
        }
    }

}