package com.example.lab9.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "charCode")
    @SerializedName("CharCode")
    val charCode: String,
    @ColumnInfo(name = "name")
    @SerializedName("Name")
    val name: String,
    @ColumnInfo(name = "nominal")
    @SerializedName("Nominal")
    val nominal: Int,
    @ColumnInfo(name = "value")
    @SerializedName("Value")
    val value: Double
)
