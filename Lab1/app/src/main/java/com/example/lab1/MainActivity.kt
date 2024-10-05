package com.example.lab1

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Locale
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var convertedCurrencyName: TextView
    private lateinit var convertedCurrencyAmount: TextView
    private lateinit var amountEdit: TextView
    private lateinit var currencies : ArrayList<Currency>
    private lateinit var convertButton: Button
    private lateinit var refreshButton: FloatingActionButton
    lateinit var selected : Currency


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        convertedCurrencyName = findViewById(R.id.convertedCurrencyName)
        convertedCurrencyAmount = findViewById(R.id.convertedCurrencyAmount)
        amountEdit = findViewById(R.id.editAmount)
        convertButton = findViewById(R.id.convertButton)
        currencies  = ArrayList()
        selected = Currency("default", 0, "", 0.0)
        loadText()

        refreshButton = findViewById(R.id.refreshButton)
        refreshButton.setOnClickListener{
            val myService: ExecutorService = Executors.newFixedThreadPool(2)
            val exchangeRateLoader = ExchangeRateLoader()
            val res = myService.submit( Callable{
                exchangeRateLoader.load()
            }
            )
            currencies = res.get()
            createRecyclerView()
        }

        convertButton.setOnClickListener{
            if (selected.name != "default") {
                val convert: Double
                if (amountEdit.text.isNotEmpty()) {
                    convert = amountEdit.text.toString().toDouble() / (selected.value / selected.nominal)
                    convertedCurrencyAmount.text = String.format(Locale.ROOT,"%.4f", convert)
                    convertedCurrencyName.text = selected.name
                }
            }
        }
    }

    private fun createRecyclerView(){
        val currencyList: RecyclerView = findViewById(R.id.currencyList)
        currencyList.addItemDecoration(DividerItemDecoration(currencyList.context, DividerItemDecoration.VERTICAL))

        val stateClickListener : CurrencyAdapter.OnStateClickListener = object : CurrencyAdapter.OnStateClickListener {
            override fun onStateClick(currency: Currency, position: Int) {
                selected = currency
            }
        }
        currencyList.layoutManager = LinearLayoutManager(this)
        currencyList.adapter = CurrencyAdapter(currencies, this, stateClickListener)
    }

    private fun saveText(){
        val sPref : SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sPref.edit()
        val gson = Gson()
        val currenciesJson : String = gson.toJson(currencies)
        val selectedJson : String = gson.toJson(selected)
        editor.apply {
            editor.putString(Keys.CONVERTED_CURRENCY_NAME, convertedCurrencyName.text.toString())
            editor.putString(Keys.CONVERTED_CURRENCY_AMOUNT, convertedCurrencyAmount.text.toString())
            editor.putString(Keys.AMOUNT_EDIT, amountEdit.text.toString())
            editor.putString(Keys.CURRENCIES_JSON, currenciesJson)
            editor.putString(Keys.SELECTED_JSON, selectedJson)
        }.apply()
    }

    private fun loadText(){
        val sPref : SharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        convertedCurrencyName.text = sPref.getString(Keys.CONVERTED_CURRENCY_NAME, "")
        convertedCurrencyAmount.text = sPref.getString(Keys.CONVERTED_CURRENCY_AMOUNT, "")
        amountEdit.text = sPref.getString(Keys.AMOUNT_EDIT, "")
        val currencyJson = sPref.getString(Keys.CURRENCIES_JSON, null)
        val selectedJson = sPref.getString(Keys.SELECTED_JSON, null)
        val typeCurrency: Type = object : TypeToken<ArrayList<Currency?>?>() {}.type
        val typeSelected : Type = object : TypeToken<Currency?>() {}.type
        if (currencyJson != null) {
            currencies = gson.fromJson<Any>(currencyJson, typeCurrency) as ArrayList<Currency>
            createRecyclerView()
        }
        if (selectedJson != null) {
            selected = gson.fromJson<Any>(selectedJson, typeSelected) as Currency
        }
    }

    override fun onPause() {
        super.onPause()
        saveText()
    }
}