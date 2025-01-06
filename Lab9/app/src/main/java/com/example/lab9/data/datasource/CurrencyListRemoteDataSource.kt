package com.example.lab9.data.datasource

import com.example.lab9.domain.Currency
import io.reactivex.Single
import javax.inject.Inject

class CurrencyListRemoteDataSource @Inject constructor(private val currencyApi: CurrencyApi) {

    fun fetchCurrencies(): Single<List<Currency>> {
        return currencyApi.getCurrencies().flatMap {
            convertToList(it)
        }
    }

    private fun convertToList(currenciesResponse: CurrenciesResponse): Single<List<Currency>> {
        val currencies = currenciesResponse.currencies.values.toList()
        return Single.just(currencies)
    }
}