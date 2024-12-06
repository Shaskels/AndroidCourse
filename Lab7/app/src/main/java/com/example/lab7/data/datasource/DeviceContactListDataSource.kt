package com.example.lab7.data.datasource

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.provider.ContactsContract
import android.util.Log
import com.example.lab7.domain.presentation.ContactFromDevice
import io.reactivex.Observable

class DeviceContactListDataSource(private val context: Context) : ContactListDeviceDataSource {
    override fun getAllContacts(): Observable<List<ContactFromDevice>> {
        return Observable.just(fetchContacts())
    }

    @SuppressLint("Range")
    private fun fetchContacts(): List<ContactFromDevice> {
        val contacts = ArrayList<ContactFromDevice>()
        if (context.checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            val cursor = context.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            if (cursor != null) {
                Log.d("contacts", cursor.count.toString())
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY))
                    val number =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    contacts.add(ContactFromDevice(id.toInt(), name, number))
                    Log.d("Contact", "$name   $id   $number")
                }
                cursor.close()
            }
        }
        return contacts
    }
}