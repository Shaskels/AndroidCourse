package com.example.lab3

class Song(var name : String, var authorName : String) : ListItem {
    override val type: Int = ListItem.Type.SONG.value
}