package org.sample.cmpproject

import KoinInitializer
import android.app.Application
import android.content.Context

class KotlinProjApp : Application() {

    companion object {
        lateinit var context : Context
    }


    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
        context = this

    }
}