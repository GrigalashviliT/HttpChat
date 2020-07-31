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
    fun addMessageToChat(item: MessageItem): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChatToList(items: List<ChatItem>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChatItem(item: MessageItem)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateChatItems(item: List<ChatItem>)

    @Delete
    suspend fun removeMessageFromChat(item: MessageItem)

    @Delete
    suspend fun removeChatToList(item: List<ChatItem>)
}