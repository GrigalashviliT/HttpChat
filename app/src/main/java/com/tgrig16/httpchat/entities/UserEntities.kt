package com.tgrig16.httpchat.entities

data class Users(
    val users: List<User>
)

data class User(
    val imageB64: String,
    val nickname: String,
    val whatIDo: String,
    val id: Long
)