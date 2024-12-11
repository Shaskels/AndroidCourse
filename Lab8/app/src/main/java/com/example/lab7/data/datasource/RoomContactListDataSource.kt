package com.example.lab7.data.datasource

import com.example.lab7.domain.presentation.ContactFromLocal
import io.reactivex.Completable
import io.reactivex.Single

class RoomContactListDataSource(private val dao: Dao) : ContactListLocalDataSource {

    override fun getAllContacts(): Single<List<ContactFromLocal>> = dao.getAllContacts()
    override fun appendContact(contact: ContactFromLocal): Completable = dao.insertContact(contact)
    override fun updateContact(contact: ContactFromLocal): Completable = dao.updateContact(contact)
}