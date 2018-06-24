package com.jagoancoding.tobuy

import android.app.Application

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ShoppingListRepo.init(this)
    }
}