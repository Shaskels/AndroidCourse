package com.example.lab7.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab7.domain.usecase.GetContactListFromDeviceUseCase
import com.example.lab7.domain.usecase.GetContactListFromLocalUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactsListViewModel @Inject constructor(
    private val getContactListFromLocalUseCase: GetContactListFromLocalUseCase,
    private val getContactListFromDeviceUseCase: GetContactListFromDeviceUseCase
) : ViewModel() {
    private val _state: MutableLiveData<ContactsListViewState> =
        MutableLiveData<ContactsListViewState>()
    val state: LiveData<ContactsListViewState> = _state
    private val compositeDisposable = CompositeDisposable()

    fun loadContacts() {
        _state.value = ContactsListViewState.Loading

        val zipper = BiFunction<List<ListItem>, List<ListItem>, List<ListItem>> { a, b -> a + b }
        val observable = Observable.zip(
            getContactListFromDeviceUseCase(),
            getContactListFromLocalUseCase(),
            zipper
        )
        val dispose = observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                _state.value = ContactsListViewState.Success(response)
            })

        compositeDisposable.add(dispose)
    }
}

