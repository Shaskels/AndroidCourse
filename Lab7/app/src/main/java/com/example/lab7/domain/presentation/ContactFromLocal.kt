package com.example.lab7.domain.presentation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactFromLocal(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "number")
    var number: String
) : ListItem {

    @Ignore
    override val type: Int = ListItem.Type.CONTACT_FROM_LOCAL.value
}

