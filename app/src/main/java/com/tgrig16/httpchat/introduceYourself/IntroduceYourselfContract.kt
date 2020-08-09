package com.tgrig16.httpchat.introduceYourself

interface IntroduceYourselfContract {

    interface Presenter {
        fun uploadImage()
        fun clickStart(name: String, whatIDo: String)
    }

}