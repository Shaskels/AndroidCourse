package com.example.lab7.presentation

interface ListItem {
    enum class Type(val value: Int) { CONTACT_FROM_LOCAL(0), CONTACT_FROM_DEVICE(1) }

    val type: Int
}