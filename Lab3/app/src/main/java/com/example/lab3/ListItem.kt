package com.example.lab3

interface ListItem {
    enum class Type(value: Int) { Song(0), Advertisement(1) }
    fun getListItemType(): Int
}