package com.tgrig16.httpchat.network

import android.util.Log
import com.tgrig16.httpchat.entities.ConnectionStatus
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface API {

    companion object {
        fun init() : API {
            val retrofit = Retrofit.Builder().baseUrl("http://localhost:5500/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            return retrofit.create(API::class.java)
        }
    }

    @GET("check-connection")
    fun checkConnection(): Call<ConnectionStatus>

}