package com.tgrig16.httpchat.DATABASE

import androidx.room.Embedded
import androidx.room.Relation

class MessageItemsAndChatItemArray
{
    @Embedded
    var item: MessageItem? = null
    @Relation(parentColumn = "remoteId", entityColumn = "parentId", entity = ChatItem::class)
    var chats: MutableList<ChatItem> = ArrayList()
}