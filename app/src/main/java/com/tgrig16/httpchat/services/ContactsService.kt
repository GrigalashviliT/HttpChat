package com.tgrig16.httpchat.services

import android.content.Context
import com.tgrig16.httpchat.database.DatabaseManager
import com.tgrig16.httpchat.database.entities.Contact

class ContactsService(context: Context) {

    val database = DatabaseManager.getDatabase(context).getChatListDao()

    fun getUserContacts(userId: Long): List<Long> {
        return database.getUserContacts(userId)
    }

    fun addContact(firstUserId: Long, secondUserId: Long) {
        database.addContact(Contact(firstUserId, secondUserId))
        database.addContact(Contact(secondUserId, firstUserId))
    }

    fun removeContact(firstUserId: Long, secondUserId: Long) {
        database.deleteContact(Contact(firstUserId, secondUserId))
    }

}