package com.tgrig16.httpchat.network

import com.tgrig16.httpchat.entities.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {

    companion object {
        fun init() : API {
            val retrofit = Retrofit.Builder().baseUrl("http://localhost:5500/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(API::class.java)
        }
    }

    @GET("check-connection")
    fun checkConnection(): Call<ConnectionEntities>

    @POST("register")
    fun registerUser(@Body data: RegisterData): Call<RegisterStatus>

    @GET("users")
    fun getUsers(): Call<Users>

    @GET("contacts")
    fun getContacts(@Body id: Long): Call<Contacts>

    @POST("send-message")
    fun sendMessage(@Body message: Message): Call<MessageSentStatus>

    @POST("messages")
    fun getMessages(@Body lastMessageRequest: LastMessageRequest): Call<Messages>

    @POST("last-message")
    fun getLastMessage(@Body lastMessageRequest: LastMessageRequest): Call<Message>

}