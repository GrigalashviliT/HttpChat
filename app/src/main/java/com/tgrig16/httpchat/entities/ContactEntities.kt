package com.tgrig16.httpchat.entities

data class Contacts (
    val contacts: List<Contact>
    )

data class Contact(
    val userId: Long
)