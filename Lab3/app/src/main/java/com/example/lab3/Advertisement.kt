package com.example.lab3

class Advertisement(var theme : String, var title : String, var disc : String) : ListItem{
    override val type: Int = ListItem.Type.ADVERTISEMENT.value
}