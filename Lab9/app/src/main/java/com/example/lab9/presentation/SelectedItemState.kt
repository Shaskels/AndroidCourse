package com.example.lab9.presentation

import com.example.lab9.domain.Currency

sealed class SelectedItemState {
    data class Success(val currency: Currency) : SelectedItemState()
}