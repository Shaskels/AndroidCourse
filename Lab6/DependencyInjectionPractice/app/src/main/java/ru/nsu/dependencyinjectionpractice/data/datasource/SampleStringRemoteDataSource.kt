package ru.nsu.dependencyinjectionpractice.data.datasource

class SampleStringRemoteDataSource : SampleStringDataSource {

	private val mockedAnswer = "String from remote data source"
	override fun get(): String = mockedAnswer
}