package com.example.lab7.domain.presentation

data class ContactFromDevice(
    var id: Int? = null,
    var name: String,
    var number: String
) : ListItem {
    override val type: Int = ListItem.Type.CONTACT_FROM_DEVICE.value
}