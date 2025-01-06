package com.example.lab9.data.datasource

import com.example.lab9.domain.Currency
import com.google.gson.annotations.SerializedName

data class CurrenciesResponse(
    @SerializedName("Valute")
    val currencies: Map<String, Currency>
)