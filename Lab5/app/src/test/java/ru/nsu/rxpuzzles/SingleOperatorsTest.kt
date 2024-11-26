package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class SingleOperatorsTest {

	@Rule
	@JvmField
	val testRule = TestSchedulerRule()

	@Test
	fun test_task1() {
		SingleOperators.Task1.solve()
			.test()
			.assertResult("Hello")
	}

	@Test
	fun test_task2() {
		val chat = object : SingleOperators.Task2.Chat {
			var listener: SingleOperators.Task2.MessageListener? = null

			override fun setInitListener(listener: SingleOperators.Task2.MessageListener) {
				this.listener = listener
			}
		}
		val name = "chat"
		val error = Exception("Fatal exception")

		val successObserver = SingleOperators.Task2.solve(chat)
			.test()

		chat.listener?.onSuccess?.invoke(name)
		successObserver.assertResult(name)

		val errorObserver = SingleOperators.Task2.solve(chat)
			.test()

		chat.listener?.onFatalError?.invoke(error)
		errorObserver.assertError { it.message == "Fatal exception" }
	}

	@Test
	fun test_task3() {
		val provider = object : SingleOperators.Task3.DataProvider {
			var counter = 0
			override fun get(): Int {
				return counter++
			}
		}

		val observable = SingleOperators.Task3.solve(provider)

		observable.test()
			.assertResult(0)

		observable.test()
			.assertResult(1)

		observable.test()
			.assertResult(2)
	}

	@Test
	fun test_task4() {
		SingleOperators.Task4.solve(Observable.just(-1))
			.test()
			.assertResult(-2)
	}

	@Test
	fun test_task5() {
		val name = "Vasya"
		val id = 1L
		val expected = SingleOperators.Task5.User(name, id)
		val apiImpl = object : SingleOperators.Task5.UserApi {
			private var name = ""

			override fun create(name: String): Single<Long> =
				Single.just(id).also { this.name = name }

			override fun get(id: Long): Single<SingleOperators.Task5.User> =
				Single.fromCallable { SingleOperators.Task5.User(this.name, id) }
		}

		SingleOperators.Task5.solve(name, apiImpl)
			.test()
			.assertResult(expected)
	}

	@Test
	fun test_task6() {
		val subscribeCounter = AtomicInteger()

		val userApi = object : SingleOperators.Task6.UserApi {
			override fun getId(): Single<Long> =
				Single.just(1L)
					.doOnSubscribe { subscribeCounter.incrementAndGet() }

			override fun getName(): Single<String> =
				Single.just("Vasya")
					.delay(2, TimeUnit.SECONDS)
					.doOnSubscribe { subscribeCounter.incrementAndGet() }

			override fun getAge(): Single<Int> =
				Single.just(20)
					.delay(5, TimeUnit.SECONDS)
					.doOnSubscribe { subscribeCounter.incrementAndGet() }
		}

		val testObserver = SingleOperators.Task6.solve(userApi)
			.test()
			.assertEmpty()

		assertEquals(3, subscribeCounter.get())

		testRule.scheduler.advanceTimeBy(3, TimeUnit.SECONDS)
		testObserver.assertEmpty()

		testRule.scheduler.advanceTimeBy(2, TimeUnit.SECONDS)
		testObserver.assertResult(SingleOperators.Task6.User(1L, "Vasya", 20))
	}
}