package com.example.lab7.domain.presentation

sealed class ContactsListViewState {
    object Loading : ContactsListViewState()

    data class Success(val contacts: List<ListItem>) : ContactsListViewState()
}