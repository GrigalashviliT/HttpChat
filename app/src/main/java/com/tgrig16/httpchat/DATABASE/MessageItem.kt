package com.tgrig16.httpchat.DATABASE

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(
    tableName = "chat", foreignKeys = [
        ForeignKey(
            entity = ChatItem::class,
            parentColumns = arrayOf("remoteId"),
            childColumns = arrayOf("parentId"),
            onDelete = CASCADE
        )
    ], indices = [
        Index(value = ["parentId"])
    ]
)
data class MessageItem(
    var message: String,
    var isMyMessage: Boolean,
    var time: String,
    var parentId: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localId")
    var id: Long = 0
}