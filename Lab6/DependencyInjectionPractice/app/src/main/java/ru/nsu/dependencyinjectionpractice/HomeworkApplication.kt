package ru.nsu.dependencyinjectionpractice

import android.app.Application
import android.content.Context

class HomeworkApplication : Application(){

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is HomeworkApplication -> appComponent
        else -> applicationContext.appComponent
    }