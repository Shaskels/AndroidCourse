package ru.nsu.dependencyinjectionpractice.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dagger.Component
import dagger.Provides
import ru.nsu.dependencyinjectionpractice.domain.usecase.GetSampleStringFromLocalUseCase
import ru.nsu.dependencyinjectionpractice.domain.usecase.GetSampleStringFromRemoteUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
	private val getSampleStringFromLocalUseCase: GetSampleStringFromLocalUseCase,
	private val getSampleStringFromRemoteUseCase: GetSampleStringFromRemoteUseCase
	) : ViewModel() {

	private val _state: MutableLiveData<MainState> = MutableLiveData<MainState>()
	val state: LiveData<MainState> = _state

	fun loadStrings() {
		_state.value = MainState.Loading

		val fromLocal = getSampleStringFromLocalUseCase()
		val fromRemote = getSampleStringFromRemoteUseCase()

		_state.value = MainState.Success(remoteString = fromRemote, localString = fromLocal)
	}

}

class MainViewModelFactory @Inject constructor(
	private val getSampleStringFromLocalUseCase: GetSampleStringFromLocalUseCase,
	private val getSampleStringFromRemoteUseCase: GetSampleStringFromRemoteUseCase
	) : ViewModelProvider.Factory{

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return MainViewModel(getSampleStringFromLocalUseCase, getSampleStringFromRemoteUseCase) as T
	}

}