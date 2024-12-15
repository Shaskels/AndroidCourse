package com.example.lab9.di

import com.example.lab9.data.repository.CurrencyListRepositoryImpl
import com.example.lab9.domain.repository.CurrencyListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsCurrencyListRepository(currencyListRepositoryImpl: CurrencyListRepositoryImpl): CurrencyListRepository
}