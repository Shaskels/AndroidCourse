package com.example.lab9.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab9.domain.Currency

@Database(entities = [Currency::class], version = 2)
abstract class MainDB : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): MainDB {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDB::class.java,
                "Currency.db"
            ).build()
        }
    }
}