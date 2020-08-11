package com.tgrig16.httpchat.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "messages_table")
data class Message (
    var senderId: Long,
    var receiverId: Long,
    var time: Date,
    var text: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}