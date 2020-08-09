package com.tgrig16.httpchat.DATABASE

import androidx.room.*
import io.reactivex.Single

@Dao
interface ChatDao {

    @Transaction
    @Query("SELECT * FROM messages WHERE remoteId = :id")
    fun loadChat(id: Long): Single<MessageItemsAndChatItemArray>

    @Transaction
    @Query("SELECT * FROM messages WHERE  personName = :personName and remoteId >= :start LIMIT 10 ")
    fun loadChat(start: Long , personName: String): Single<MessageItemsAndChatItemArray>

    @Transaction
    @Query("SELECT * FROM messages   LIMIT (select count (*) FROM chat WHERE  localId >= :start and parentId = :parentId LIMIT 10) ")
    fun loadStringList(start: Long , parentId: Long): Single<MessageItemsAndChatItemArray>


    @Transaction
    @Query("SELECT * FROM messages WHERE personName = :personName")
    fun loadChat(personName: String): Single<MessageItemsAndChatItemArray>

    @Transaction
    @Query("SELECT remoteId FROM messages WHERE personName = :personName")
    fun loadParentId(personName: String): Single<Long>

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