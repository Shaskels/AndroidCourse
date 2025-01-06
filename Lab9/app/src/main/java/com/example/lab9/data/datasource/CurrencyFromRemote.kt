package com.example.lab9.data.datasource

import com.google.gson.annotations.SerializedName

data class CurrencyFromRemote(
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("Value")
    val value: Double
)
