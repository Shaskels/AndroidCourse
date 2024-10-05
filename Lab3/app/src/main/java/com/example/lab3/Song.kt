package com.example.lab3

class Song(var name : String, var authorName : String) : ListItem {
    override fun getListItemType(): Int {
        return ListItem.Type.Song.ordinal
    }
}