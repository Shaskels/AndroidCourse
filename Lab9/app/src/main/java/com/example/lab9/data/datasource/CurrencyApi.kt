package com.example.lab9.data.datasource

import io.reactivex.Single
import retrofit2.http.GET

interface CurrencyApi {

    @GET("./daily_json.js")
    fun getCurrencies(): Single<CurrenciesResponse>
}