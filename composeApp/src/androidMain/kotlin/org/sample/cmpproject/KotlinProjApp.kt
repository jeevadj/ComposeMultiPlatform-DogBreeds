package org.sample.cmpproject

import KoinInitializer
import android.app.Application

class KotlinProjApp : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}