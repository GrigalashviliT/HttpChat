package com.tgrig16.httpchat.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact (
    var firstUserId: Long,
    var secondUserId: Long
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}