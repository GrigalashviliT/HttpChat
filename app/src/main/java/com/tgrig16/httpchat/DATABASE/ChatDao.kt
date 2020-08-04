package com.tgrig16.httpchat.DATABASE

import androidx.room.*
import io.reactivex.Single

@Dao
interface ChatDao {
//    @Transaction
//    @Query("SELECT * FROM chat WHERE personName like :searchKey")
//    fun loadPersonS(searchKey: String): Single<List<MessageItemsAndChatItemArray>>

    @Transaction
    @Query("SELECT * FROM messages WHERE remoteId = :id")
    fun loadChat(id: Long): Single<MessageItemsAndChatItemArray>

    @Insert
    fun addMessageToChat(item: ChatItem): Single<Long>

    @Insert
    fun addChatToList(items: List<MessageItem>)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChatItem(item: ChatItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChatItems(item: List<MessageItem>)

    @Delete
    suspend fun removeMessageFromChat(item: ChatItem)

    @Delete
    suspend fun removeChatToList(item: List<MessageItem>)
}