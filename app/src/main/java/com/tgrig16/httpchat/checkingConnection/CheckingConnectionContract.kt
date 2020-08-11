package com.tgrig16.httpchat.checkingConnection

interface CheckingConnectionContract {

    interface View {
        fun displaySuccess()
        fun displayError()
    }

    interface Presenter {
        fun checkConnection()
    }

}