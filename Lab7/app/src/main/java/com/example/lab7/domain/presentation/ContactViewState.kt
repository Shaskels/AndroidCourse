package com.example.lab7.domain.presentation

sealed class ContactViewState {
    object Loading : ContactViewState()

    data class Success(val contact: ContactFromLocal) : ContactViewState()
}