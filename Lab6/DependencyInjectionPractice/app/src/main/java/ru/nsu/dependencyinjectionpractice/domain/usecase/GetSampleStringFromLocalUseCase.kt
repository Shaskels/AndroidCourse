package ru.nsu.dependencyinjectionpractice.domain.usecase

import ru.nsu.dependencyinjectionpractice.domain.repository.SampleStringRepository
import javax.inject.Inject


class GetSampleStringFromLocalUseCase @Inject constructor(
	private val repository: SampleStringRepository
	) {

	operator fun invoke(): String {
		val fromLocal = repository.getFromLocal()
		val repoInstanceHash = repository.hashCode()

		return "$fromLocal, repo hash = $repoInstanceHash"
	}
}