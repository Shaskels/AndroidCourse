package com.example.lab9.data.datasource

import com.example.lab9.domain.Currency
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CurrencyListLocalDataSource @Inject constructor(private val dao: Dao) {
    fun getAllCurrencies(): Single<List<Currency>> = dao.getAllCurrencies()
    fun updateCurrency(currency: Currency): Completable = dao.updateCurrency(currency)
    fun insertCurrency(currency: Currency): Completable = dao.insertCurrency(currency)
}