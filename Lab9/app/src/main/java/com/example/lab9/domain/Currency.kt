package com.example.lab9.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "charCode")
    val charCode: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "nominal")
    val nominal: Int,
    @ColumnInfo(name = "value")
    val value: Double
)
