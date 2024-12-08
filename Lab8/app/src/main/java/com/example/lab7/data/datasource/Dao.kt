package com.example.lab7.data.datasource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lab7.domain.presentation.ContactFromLocal
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface Dao {
    @Insert
    fun insertContact(contact: ContactFromLocal): Completable

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Single<List<ContactFromLocal>>

    @Update
    fun updateContact(contact: ContactFromLocal): Completable
}