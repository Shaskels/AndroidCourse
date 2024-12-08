package com.example.lab7.domain.repository

import com.example.lab7.presentation.ContactFromDevice
import com.example.lab7.presentation.ContactFromLocal
import io.reactivex.Completable
import io.reactivex.Observable

interface ContactListRepository {
    fun fetchContactListFromDevice(): Observable<List<ContactFromDevice>>

    fun fetchContactList(): Observable<List<ContactFromLocal>>

    fun appendContactList(contact: ContactFromLocal): Completable

    fun updateContactInList(contact: ContactFromLocal): Completable
}