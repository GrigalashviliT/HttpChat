package com.tgrig16.httpchat.database

import androidx.room.*
import com.tgrig16.httpchat.database.entities.Contact
import com.tgrig16.httpchat.database.entities.Message
import com.tgrig16.httpchat.database.entities.User

@Dao
interface ChatDao {

    @Insert
    fun registerUser(user: User): Long

    @Query("select * from users_table")
    fun getAllUsers(): List<User>

    @Query("select id from users_table where nickname = :nickname limit 1")
    fun getUserByNickname(nickname: String): Long?

    @Insert
    fun addContact(contact: Contact)

    @Query("select secondUserId from contacts_table where firstUserId = :userId")
    fun getUserContacts(userId: Long): List<Long>

    @Delete
    fun deleteContact(contact: Contact)

    @Insert
    fun addMessage(message: Message)

    @Query("select * from messages_table where (senderId = :firstUseId and receiverId = :secondUserId) or (senderId = :secondUserId and receiverId = :firstUseId) order by time ASC")
    fun getMessages(firstUseId: Long, secondUserId: Long): List<Message>

    @Query("select * from messages_table where (senderId = :firstUseId and receiverId = :secondUserId) or (senderId = :secondUserId and receiverId = :firstUseId) order by time ASC limit 1")
    fun getLastMessageBetween(firstUseId: Long, secondUserId: Long): Message

}