package com.example.lab1

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ExchangeRateLoader {
    fun load() : ArrayList<Currency> {
        var data = ""
        val valutes = ArrayList<Currency>()
        val url = URL("https://www.cbr-xml-daily.ru/daily_json.js")
        val connection = url.openConnection() as HttpURLConnection
        val inputStream : InputStream = connection.inputStream
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line : String?

        do {
            line = bufferedReader.readLine()
            data += line
        }
        while (line != null)

        if (data.isNotEmpty()){
            Log.v("some", data)
            val jsonObject = JSONObject(data)
            val valute = jsonObject.getJSONObject("Valute")
            val keys = valute.keys()
            keys.forEach {
                val valu = valute.getJSONObject(it)
                val charCode = valu.getString("CharCode")
                val nominal : Int = valu.getString("Nominal").toInt()
                val name = valu.getString("Name")
                val value = valu.getString("Value").toDouble()
                valutes.add(Currency(charCode, nominal, name, value))
            }
        }
        return valutes
    }
}
