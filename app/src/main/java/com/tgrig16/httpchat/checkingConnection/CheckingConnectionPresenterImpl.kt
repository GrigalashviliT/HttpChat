package com.tgrig16.httpchat.checkingConnection

import com.tgrig16.httpchat.entities.ConnectionEntities
import com.tgrig16.httpchat.network.API
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckingConnectionPresenterImpl(var view: CheckingConnectionContract.View): CheckingConnectionContract.Presenter {

    private val api by lazy {
        API.init()
    }

    override fun checkConnection() {
        val call = api.checkConnection()

        call.enqueue(object: Callback<ConnectionEntities> {

            override fun onResponse(call: Call<ConnectionEntities>, response: Response<ConnectionEntities>) {
                val statusSuccess = response.body()?.success

                if (statusSuccess != null && statusSuccess) {
                    view.displaySuccess()
                } else {
                    view.displayError()
                }
            }

            override fun onFailure(call: Call<ConnectionEntities>, t: Throwable) {
                view.displayError()
            }

        })
    }

}