package com.example.lab9.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab9.domain.Currency
import com.example.lab9.domain.usecase.GetCurrencyListFromLocalUseCase
import com.example.lab9.domain.usecase.GetCurrencyListFromRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CurrenciesListViewModel @Inject constructor(
    private val getCurrencyListFromLocalUseCase: GetCurrencyListFromLocalUseCase,
    private val getCurrencyListFromRemoteUseCase: GetCurrencyListFromRemoteUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _currencyListState: MutableLiveData<CurrenciesListViewState> =
        MutableLiveData<CurrenciesListViewState>()
    private var _selectedItemState: MutableLiveData<SelectedItemState> =
        MutableLiveData<SelectedItemState>()
    val selectedItemState: LiveData<SelectedItemState> = _selectedItemState
    val currencyListState: LiveData<CurrenciesListViewState> = _currencyListState

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun fetchCurrenciesListFromRemote() {
        _currencyListState.value = CurrenciesListViewState.Loading

        compositeDisposable.add(getCurrencyListFromRemoteUseCase()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _currencyListState.value = CurrenciesListViewState.Success(response)
            }, { e ->
                Log.d("Fail", e.message.toString())
            })
        )
    }

    fun fetchCurrenciesListFromLocal() {
        _currencyListState.value = CurrenciesListViewState.Loading

        compositeDisposable.add(getCurrencyListFromLocalUseCase()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _currencyListState.value = CurrenciesListViewState.Success(response)
            }, { e ->
                Log.d("Fail", e.message.toString())
            })
        )
    }

    fun setSelectedItem(currency: Currency) {
        _selectedItemState.value = SelectedItemState.Success(currency)
    }

}