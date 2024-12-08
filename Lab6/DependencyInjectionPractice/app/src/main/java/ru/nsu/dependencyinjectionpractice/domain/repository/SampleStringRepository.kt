package ru.nsu.dependencyinjectionpractice.domain.repository

interface SampleStringRepository {
    fun getFromRemote(): String
    fun getFromLocal(): String
}