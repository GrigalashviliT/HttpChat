package com.tgrig16.httpchat.DATABASE

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class ChatItem(
    var personName: String
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "remoteId")
    var id:Long = 0
}