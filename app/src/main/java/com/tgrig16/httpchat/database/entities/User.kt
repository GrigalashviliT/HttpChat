package com.tgrig16.httpchat.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User (
    var imageB64: String,
    var nickname: String,
    var whatIDo: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}