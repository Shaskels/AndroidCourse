package com.example.lab7

import android.app.Application
import android.content.Context
import com.example.lab7.di.AppComponent
import com.example.lab7.di.DaggerAppComponent

class ContactsApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }
}
