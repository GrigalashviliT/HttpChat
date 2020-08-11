package com.tgrig16.httpchat.entities

data class RegisterData(
    val imageB64: String,
    val nickname: String,
    val whatIDo: String
)

data class RegisterStatus (
    val id: Long
)