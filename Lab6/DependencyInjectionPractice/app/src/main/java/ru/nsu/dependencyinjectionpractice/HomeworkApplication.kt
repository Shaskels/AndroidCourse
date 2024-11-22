package ru.nsu.dependencyinjectionpractice

import android.app.Application
import ru.nsu.dependencyinjectionpractice.di.AppComponent
import ru.nsu.dependencyinjectionpractice.di.DaggerAppComponent

class HomeworkApplication : Application(){

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}