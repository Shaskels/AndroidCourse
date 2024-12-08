package com.example.lab7.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab7.domain.presentation.ContactFromLocal

@Database(entities = [ContactFromLocal::class], version = 1)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "Contacts.db"
            ).build()
        }
    }
}