package com.example.lab9.domain.repository

import com.example.lab9.domain.Currency
import io.reactivex.Completable
import io.reactivex.Single

interface CurrencyListRepository {
    fun getFromRemote(): Single<List<Currency>>
    fun getFromLocal(): Single<List<Currency>>
    fun updateInLocal(currency: Currency): Completable
    fun insertInLocal(currency: Currency): Completable
}