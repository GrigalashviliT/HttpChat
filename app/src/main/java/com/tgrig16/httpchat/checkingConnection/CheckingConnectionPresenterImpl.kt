package com.tgrig16.httpchat.checkingConnection

import com.tgrig16.httpchat.entities.ConnectionStatus
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

        call.enqueue(object: Callback<ConnectionStatus> {

            override fun onResponse(call: Call<ConnectionStatus>, response: Response<ConnectionStatus>) {
                val statusSuccess = response.body()?.success

                if (statusSuccess != null && statusSuccess) {
                    view.displaySuccess()
                } else {
                    view.displayError()
                }
            }

            override fun onFailure(call: Call<ConnectionStatus>, t: Throwable) {
                view.displayError()
            }

        })
    }

}