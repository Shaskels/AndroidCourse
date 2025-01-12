package ru.nsu.dependencyinjectionpractice.data.repository

import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringDataSource
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringLocalDataSource
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringRemoteDataSource
import ru.nsu.dependencyinjectionpractice.domain.repository.SampleStringRepository
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class SampleStringRepositoryImpl @Inject constructor(
	@param:Named("Local")
	private val localDataSource: SampleStringDataSource,
	@param:Named("Remote")
	private val remoteDataSource: SampleStringDataSource
	) : SampleStringRepository {

	override fun getFromRemote(): String =
		remoteDataSource.get()

	override fun getFromLocal(): String =
		localDataSource.get()
}