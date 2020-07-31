package com.tgrig16.httpchat.WelcomePage

interface WelcomeContract {
    interface View {

    }

    interface Presenter {
        fun changedWhatIDoText(text:String)
        fun changedNameText(text: String)
        fun uploadImage()
        fun clickStart()
    }
}