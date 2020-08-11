package com.tgrig16.httpchat.introduceYourself

interface IntroduceYourselfContract {

    interface Presenter {
        fun uploadImage()
        fun clickStart(imageB64: String, name: String, whatIDo: String)
    }

}