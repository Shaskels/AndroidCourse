package com.example.lab7.presentation

sealed class ContactsListViewState {
    object Loading : ContactsListViewState()

    data class Success(val contacts: List<ListItem>) : ContactsListViewState()
}