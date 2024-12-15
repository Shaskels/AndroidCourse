package com.example.lab9.domain.usecase

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.lab9.domain.repository.CurrencyListRepository
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class GetCurrencyListFromRemoteService : Service() {
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var repository: CurrencyListRepository

    override fun onCreate() {
        Log.d("Service", "Create")
        super.onCreate()
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Start Command")
        compositeDisposable.add(repository.getFromRemote()
            .map { it.forEach { repository.updateInLocal(it) }; it }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Success", "Update success")
            }, {
                Log.d("Error", "Update failed")
            })
        )
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}