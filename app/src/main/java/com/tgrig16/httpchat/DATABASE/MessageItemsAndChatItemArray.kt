package com.tgrig16.httpchat.DATABASE

import androidx.room.Embedded
import androidx.room.Relation

class MessageItemsAndChatItemArray
{

    @Embedded
    var item: ChatItem? = null
    @Relation(parentColumn = "remoteId", entityColumn = "parentId", entity = MessageItem::class)
    var chats: MutableList<MessageItem> = ArrayList()
}