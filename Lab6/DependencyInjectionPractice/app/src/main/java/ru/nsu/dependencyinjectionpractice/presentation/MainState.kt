package ru.nsu.dependencyinjectionpractice.presentation

sealed class MainState {
    object Loading : MainState()

    data class Success(val remoteString: String, val localString: String) : MainState()
}