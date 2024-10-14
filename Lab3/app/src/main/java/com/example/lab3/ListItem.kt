package com.example.lab3

interface ListItem {
    enum class Type(val value: Int) { SONG(0), ADVERTISEMENT(1) }
    val type: Int
}