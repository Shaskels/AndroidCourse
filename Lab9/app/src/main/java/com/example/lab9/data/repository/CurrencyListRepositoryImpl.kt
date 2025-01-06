package com.example.lab9.data.repository

import com.example.lab9.data.datasource.CurrencyListLocalDataSource
import com.example.lab9.data.datasource.CurrencyListRemoteDataSource
import com.example.lab9.domain.Currency
import com.example.lab9.domain.repository.CurrencyListRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CurrencyListRepositoryImpl @Inject constructor(
    private val currencyListLocalDataSource: CurrencyListLocalDataSource,
    private val currencyListRemoteDataSource: CurrencyListRemoteDataSource
) : CurrencyListRepository {
    override fun getFromRemote(): Single<List<Currency>> =
        currencyListRemoteDataSource.fetchCurrencies()

    override fun getFromLocal(): Single<List<Currency>> =
        currencyListLocalDataSource.getAllCurrencies()

    override fun updateInLocal(currency: Currency): Completable =
        currencyListLocalDataSource.updateCurrency(currency)

    override fun insertInLocal(currency: Currency): Completable =
        currencyListLocalDataSource.insertCurrency(currency)

}