package ru.nsu.dependencyinjectionpractice.domain.repository

import javax.inject.Singleton

interface SampleStringRepository {
    fun getFromRemote(): String
    fun getFromLocal(): String
}