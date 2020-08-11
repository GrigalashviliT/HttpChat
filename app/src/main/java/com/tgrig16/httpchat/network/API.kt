package com.tgrig16.httpchat.network

import com.tgrig16.httpchat.entities.ConnectionEntities
import com.tgrig16.httpchat.entities.RegisterData
import com.tgrig16.httpchat.entities.RegisterStatus
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

}