package com.tgrig16.httpchat.DATABASE

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageItem(
    var message: String,
    var isMyMessage: Boolean,
    var time: String)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "remoteId")
    var id:Long = 0
}