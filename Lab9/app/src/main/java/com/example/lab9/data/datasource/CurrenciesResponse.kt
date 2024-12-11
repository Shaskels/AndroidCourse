package com.example.lab9.data.datasource

import com.google.gson.annotations.SerializedName

data class CurrenciesResponse(
    @SerializedName("Valute")
    val currencies: Currencies
)

data class Currencies(
    val AUD: CurrencyFromRemote,
    val AZN: CurrencyFromRemote,
    val GBP: CurrencyFromRemote,
    val AMD: CurrencyFromRemote,
    val BYN: CurrencyFromRemote,
    val BGN: CurrencyFromRemote,
    val BRL: CurrencyFromRemote,
    val HUF: CurrencyFromRemote,
    val VND: CurrencyFromRemote,
    val HKD: CurrencyFromRemote,
    val GEL: CurrencyFromRemote,
    val DKK: CurrencyFromRemote,
    val AED: CurrencyFromRemote,
    val USD: CurrencyFromRemote,
    val EUR: CurrencyFromRemote,
    val EGP: CurrencyFromRemote,
    val INR: CurrencyFromRemote,
    val IDR: CurrencyFromRemote,
    val KZT: CurrencyFromRemote,
    val CAD: CurrencyFromRemote,
    val QAR: CurrencyFromRemote,
    val KGS: CurrencyFromRemote,
    val CNY: CurrencyFromRemote,
    val MDL: CurrencyFromRemote,
    val NZD: CurrencyFromRemote,
    val NOK: CurrencyFromRemote,
    val PLN: CurrencyFromRemote,
    val RON: CurrencyFromRemote,
    val XDR: CurrencyFromRemote,
    val SGD: CurrencyFromRemote,
    val TJS: CurrencyFromRemote,
    val THB: CurrencyFromRemote,
    val TRY: CurrencyFromRemote,
    val TMT: CurrencyFromRemote,
    val UZS: CurrencyFromRemote,
    val UAH: CurrencyFromRemote,
    val CZK: CurrencyFromRemote,
    val SEK: CurrencyFromRemote,
    val CHF: CurrencyFromRemote,
    val RSD: CurrencyFromRemote,
    val ZAR: CurrencyFromRemote,
    val KRW: CurrencyFromRemote,
    val JPY: CurrencyFromRemote
)