package com.example.lab9.domain.usecase

import com.example.lab9.domain.Currency
import com.example.lab9.domain.repository.CurrencyListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCurrencyListFromRemoteUseCase @Inject constructor(
    private val repository: CurrencyListRepository
) {
    operator fun invoke(): Single<List<Currency>> {
        return repository.getFromRemote().map {
            it.forEach {
                repository.updateInLocal(it)
            };
            it
        }
    }
}