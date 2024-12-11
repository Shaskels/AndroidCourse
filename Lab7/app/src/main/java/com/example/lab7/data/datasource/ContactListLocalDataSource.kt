package com.example.lab7.data.datasource

import com.example.lab7.presentation.ContactFromLocal
import io.reactivex.Completable
import io.reactivex.Single

interface ContactListLocalDataSource {
    fun getAllContacts(): Single<List<ContactFromLocal>>
    fun appendContact(contact: ContactFromLocal): Completable
    fun updateContact(contact: ContactFromLocal): Completable
}