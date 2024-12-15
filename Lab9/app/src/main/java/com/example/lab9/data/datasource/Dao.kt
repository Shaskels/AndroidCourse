package com.example.lab9.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lab9.domain.Currency
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface Dao {
    @Insert
    fun insertCurrency(currency: Currency): Completable

    @Query("SELECT * FROM currency")
    fun getAllCurrencies(): Single<List<Currency>>

    @Update
    fun updateCurrency(currency: Currency): Completable
}