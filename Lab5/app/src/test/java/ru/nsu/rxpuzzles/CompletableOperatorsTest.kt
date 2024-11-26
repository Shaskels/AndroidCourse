package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class CompletableOperatorsTest {

	@Test
	fun test_task1() {
		CompletableOperators.Task1.solve()
			.test()
			.assertComplete()
	}

	@Test
	fun test_task2() {
		CompletableOperators.Task2.solve()
			.test()
			.assertError(Throwable::class.java)
	}

	@Test
	fun test_task3() {
		val worker = object : CompletableOperators.Task3.Worker {
			var invoked: Boolean = false
			override fun doWork() {
				invoked = true
			}
		}
		CompletableOperators.Task3.solve(worker)
			.test()
			.assertComplete()

		assertTrue(worker.invoked)
	}

	@Test
	fun test_task4() {
		val source = Single.just("Hello")

		CompletableOperators.Task4.solve(source)
			.test()
			.assertComplete()
	}

	@Test
	fun test_task5() {
		val subscribeCounter = AtomicInteger()
		val first = Completable.complete()
			.doOnSubscribe { subscribeCounter.incrementAndGet() }
		val second = Completable.complete()
			.doOnSubscribe { subscribeCounter.incrementAndGet() }
		val third = Completable.complete()
			.doOnSubscribe { subscribeCounter.incrementAndGet() }

		CompletableOperators.Task5.solve(first, second, third)
			.test()
			.assertComplete()

		assertEquals(3, subscribeCounter.get())
	}

	@Test
	fun test_task6() {
		val threads = mutableListOf<String>()

		val completable = Completable.fromAction {
			threads.add(Thread.currentThread().name)
		}
		val first = Schedulers.from(Executors.newSingleThreadExecutor { Thread(it, "First thread") })
		val second = Schedulers.from(Executors.newSingleThreadExecutor { Thread(it, "Second thread") })
		CompletableOperators.Task6.solve(completable, first)
			.doOnComplete { threads.add(Thread.currentThread().name) }
			.subscribeOn(second)
			.test()
			.await()
			.assertResult()

		assertEquals(listOf("Second thread", "First thread"), threads)
	}

	@Test
	fun test_task7() {
		val threads = mutableListOf<String>()

		val completable = Completable.fromAction {
			threads.add(Thread.currentThread().name)
		}
		val first = Schedulers.from(Executors.newSingleThreadExecutor { Thread(it, "First thread") })
		val second = Schedulers.from(Executors.newSingleThreadExecutor { Thread(it, "Second thread") })

		CompletableOperators.Task7.solve(completable, first)
			.doOnComplete { threads.add(Thread.currentThread().name) }
			.observeOn(second)
			.doOnComplete { threads.add(Thread.currentThread().name) }
			.test()
			.await()
			.assertResult()

		assertEquals(listOf("First thread", "First thread", "Second thread"), threads)
	}
}