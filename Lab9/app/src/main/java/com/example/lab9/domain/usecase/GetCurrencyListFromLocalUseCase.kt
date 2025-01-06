package com.example.lab9.domain.usecase

import com.example.lab9.domain.Currency
import com.example.lab9.domain.repository.CurrencyListRepository
import io.reactivex.Single
import javax.inject.Inject

class GetCurrencyListFromLocalUseCase @Inject constructor(
    private val repository: CurrencyListRepository
) {
    operator fun invoke(): Single<List<Currency>> {
        return repository.getFromLocal().flatMap {
            if (it.isNotEmpty()) {
                Single.just(it)
            } else {
                repository.getFromRemote().map {
                    it.forEach {
                        repository.insertInLocal(it)
                    };
                    it
                }
            }
        }
    }
}