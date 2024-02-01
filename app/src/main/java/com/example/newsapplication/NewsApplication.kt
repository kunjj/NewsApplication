package com.example.newsapplication

import android.app.Application
import android.content.Context

class NewsApplication: Application() {

    init {
        instance = this
    }
    companion object{
        lateinit var instance : NewsApplication
        fun getApplicationContext(): Context = instance.applicationContext
    }

}