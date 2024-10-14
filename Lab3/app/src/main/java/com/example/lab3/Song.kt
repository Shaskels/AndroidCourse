package com.example.lab3

class Song(var name : String, var authorName : String) : ListItem {
    override val type: Int
        get() = ListItem.Type.SONG.value
}