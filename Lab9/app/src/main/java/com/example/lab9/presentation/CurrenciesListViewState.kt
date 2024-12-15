package com.example.lab9.presentation

import com.example.lab9.domain.Currency

sealed class CurrenciesListViewState {
    object Loading : CurrenciesListViewState()

    data class Success(val currencyList: List<Currency>) : CurrenciesListViewState()
}