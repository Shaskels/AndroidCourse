package com.example.lab7.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab7.domain.usecase.AppendContactToLocalUseCase
import com.example.lab7.domain.usecase.UpdateContactInLocalUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactViewModel @Inject constructor(
    private val appendContactToLocalUseCase: AppendContactToLocalUseCase,
    private val updateContactInLocalUseCase: UpdateContactInLocalUseCase
) : ViewModel() {
    private val _state: MutableLiveData<ContactViewState> = MutableLiveData<ContactViewState>()
    val state: LiveData<ContactViewState> = _state
    private val compositeDisposable = CompositeDisposable()

    fun appendContact(contact: ContactFromLocal) {
        _state.value = ContactViewState.Loading

        val dispose = appendContactToLocalUseCase(contact)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = ContactViewState.Success(contact)
            })

        compositeDisposable.add(dispose)
    }

    fun updateContact(contact: ContactFromLocal) {
        _state.value = ContactViewState.Loading


        val dispose = updateContactInLocalUseCase(contact)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _state.value = ContactViewState.Success(contact)
            })

        compositeDisposable.add(dispose)
    }
}
