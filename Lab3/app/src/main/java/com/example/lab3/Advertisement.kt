package com.example.lab3

class Advertisement(var theme : String, var title : String, var disc : String) : ListItem{
    override fun getListItemType(): Int {
        return ListItem.Type.Advertisement.ordinal
    }
}