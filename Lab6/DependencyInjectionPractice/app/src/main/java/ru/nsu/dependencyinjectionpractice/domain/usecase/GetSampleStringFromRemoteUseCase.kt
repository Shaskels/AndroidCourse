package ru.nsu.dependencyinjectionpractice.domain.usecase

import ru.nsu.dependencyinjectionpractice.domain.repository.SampleStringRepository
import javax.inject.Inject

class GetSampleStringFromRemoteUseCase @Inject constructor(
	private val repository: SampleStringRepository
	) {

	operator fun invoke(): String {
		val fromRemote = repository.getFromRemote()
		val repoInstanceHash = repository.hashCode()

		return "$fromRemote, repo hash = $repoInstanceHash"
	}
}