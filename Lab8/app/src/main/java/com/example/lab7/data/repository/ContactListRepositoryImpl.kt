package com.example.lab7.data.repository

import com.example.lab7.data.datasource.ContactListDeviceDataSource
import com.example.lab7.domain.presentation.ContactFromLocal
import com.example.lab7.data.datasource.ContactListLocalDataSource
import com.example.lab7.domain.presentation.ContactFromDevice
import com.example.lab7.domain.repository.ContactListRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ContactListRepositoryImpl @Inject constructor(
    private val contactListLocalDataSource: ContactListLocalDataSource,
    private val contactListDeviceDataSource: ContactListDeviceDataSource
) : ContactListRepository {

    override fun fetchContactListFromDevice(): Observable<List<ContactFromDevice>> =
        contactListDeviceDataSource.getAllContacts()

    override fun fetchContactList(): Observable<List<ContactFromLocal>> =
        contactListLocalDataSource.getAllContacts().toObservable()

    override fun appendContactList(contact: ContactFromLocal): Completable =
        contactListLocalDataSource.appendContact(contact)

    override fun updateContactInList(contact: ContactFromLocal): Completable =
        contactListLocalDataSource.updateContact(contact)
}