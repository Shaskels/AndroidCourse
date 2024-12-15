package com.example.lab9.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lab9.R
import com.example.lab9.domain.Currency
import com.example.lab9.domain.usecase.GetCurrencyListFromRemoteService
import com.example.lab9.presentation.CurrenciesListViewModel
import com.example.lab9.presentation.CurrenciesListViewState
import com.example.lab9.presentation.CurrencyAdapter
import com.example.lab9.presentation.SelectedItemState
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var convertedCurrencyName: TextView
    private lateinit var convertedCurrencyAmount: TextView
    private lateinit var amountEdit: TextView
    private lateinit var convertButton: Button
    private lateinit var selected: Currency
    private lateinit var refreshLayout: SwipeRefreshLayout
    private val viewModel: CurrenciesListViewModel by viewModels()
    private lateinit var adapter: CurrencyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selected = Currency("default", "", 0, 0.0)
        val currencyList: RecyclerView = findViewById(R.id.currencyList)
        convertedCurrencyName = findViewById(R.id.convertedCurrencyName)
        convertedCurrencyAmount = findViewById(R.id.convertedCurrencyAmount)
        amountEdit = findViewById(R.id.editAmount)
        convertButton = findViewById(R.id.convertButton)
        refreshLayout = findViewById(R.id.swipeRefreshLayout)
        currencyList.addItemDecoration(
            DividerItemDecoration(
                currencyList.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val stateClickListener: CurrencyAdapter.OnStateClickListener =
            object : CurrencyAdapter.OnStateClickListener {
                override fun onStateClick(currency: Currency, position: Int) {
                    viewModel.setSelectedItem(currency)
                }
            }
        adapter = CurrencyAdapter(this, stateClickListener)
        currencyList.layoutManager = LinearLayoutManager(this)
        currencyList.adapter = adapter
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = false
            viewModel.fetchCurrenciesListFromRemote()
        }
        viewModel.currencyListState.observe(this) { newState ->
            renderListState(newState)
        }
        viewModel.selectedItemState.observe(this) { newState ->
            renderState(newState)
        }
        viewModel.fetchCurrenciesListFromLocal()

        convertButton.setOnClickListener {
            calculateMoney()
        }

        val cal: Calendar = Calendar.getInstance()
        cal.add(Calendar.SECOND, 10)
        val intent = Intent(this, GetCurrencyListFromRemoteService::class.java)

        val pintent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val alarm = getSystemService(ALARM_SERVICE) as AlarmManager
        alarm.setRepeating(
            AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
            (12 * 60 * 60 * 1000).toLong(), pintent
        )
        startService(Intent(baseContext, GetCurrencyListFromRemoteService::class.java))
    }

    private fun calculateMoney() {
        if (selected.charCode != "default") {
            val convert: Double
            if (amountEdit.text.isNotEmpty()) {
                convert =
                    amountEdit.text.toString().toDouble() / (selected.value / selected.nominal)
                convertedCurrencyAmount.text = String.format(Locale.ROOT, "%.4f", convert)
                convertedCurrencyName.text = selected.name
            }
        }
    }

    private fun renderListState(state: CurrenciesListViewState) {
        when (state) {
            CurrenciesListViewState.Loading -> {
                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
            }

            is CurrenciesListViewState.Success -> {
                adapter.setItems(state.currencyList)
            }
        }
    }

    private fun renderState(state: SelectedItemState) {
        when (state) {
            is SelectedItemState.Success -> {
                if (selected != state.currency) {
                    convertedCurrencyAmount.text = ""
                    convertedCurrencyName.text = state.currency.name
                    selected = state.currency
                }
            }
        }
    }
}