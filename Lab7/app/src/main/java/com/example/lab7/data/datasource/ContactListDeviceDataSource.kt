package com.example.lab7.data.datasource

import com.example.lab7.presentation.ContactFromDevice
import io.reactivex.Observable

interface ContactListDeviceDataSource {
    fun getAllContacts(): Observable<List<ContactFromDevice>>
}