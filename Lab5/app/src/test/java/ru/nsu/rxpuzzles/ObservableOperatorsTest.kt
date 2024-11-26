package ru.nsu.rxpuzzles

import ru.nsu.rxpuzzles.ObservableOperators.Task12.Package
import ru.nsu.rxpuzzles.ObservableOperators.Task12.Pie
import ru.nsu.rxpuzzles.ObservableOperators.Task13.File
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class ObservableOperatorsTest {

	@Rule
	@JvmField
	val testRule = TestSchedulerRule()

	@Test
	fun test_task1() {
		ObservableOperators.Task1.solve()
			.test()
			.assertResult("Hello")
	}

	@Test
	fun test_task2() {
		ObservableOperators.Task2.solve()
			.test()
			.assertResult(
				"Понедельник",
				"Вторник",
				"Среда",
				"Четверг",
				"Пятница",
				"Суббота",
				"Воскресенье",
			)
	}

	@Test
	fun test_task3() {
		val values = (1..100).toList().toTypedArray()

		ObservableOperators.Task3.solve()
			.test()
			.assertResult(*values)
	}

	@Test
	fun test_task4() {
		val values = (5 downTo 1).toList().toTypedArray()

		ObservableOperators.Task4.solve()
			.test()
			.assertValues(*values)
			.assertError {
				it.message == "Boom"
			}
	}

	@Test
	fun test_task5() {
		val list = listOf(
			"Понедельник",
			"Вторник",
			"Среда",
			"Четверг",
			"Пятница",
			"Суббота",
			"Воскресенье",
		)
		val values = list.toTypedArray()

		ObservableOperators.Task5.solve(list)
			.test()
			.assertValues(*values)
			.assertComplete()
	}

	@Test
	fun test_task6() {
		val chat = object : ObservableOperators.Task6.Chat {
			var listener: ObservableOperators.Task6.MessageListener? = null

			override fun setMessageListener(listener: ObservableOperators.Task6.MessageListener) {
				this.listener = listener
			}
		}
		val messages = arrayOf("1", "2", "3")
		val error = Exception("Fatal exception")

		val testObserver = ObservableOperators.Task6.solve(chat)
			.test()

		for (i in messages.indices) {
			chat.listener?.onMessage?.invoke(messages[i])
		}
		testObserver.assertValues(*messages)

		chat.listener?.onFatalError?.invoke(error)
		testObserver.assertError { it.message == "Fatal exception" }
	}

	@Test
	fun test_task7() {
		ObservableOperators.Task7.solve(Observable.range(0, 10))
			.test()
			.assertResult(1, 3, 5, 7, 9)
	}

	@Test
	fun test_task8() {
		val source = Observable.range(1, 10)
		ObservableOperators.Task8.solve(source)
			.test()
			.assertResult(1, 2, 3, 4, 5)
	}

	@Test
	fun test_task9() {
		val source = Observable.range(1, 10)
		ObservableOperators.Task9.solve(source)
			.test()
			.assertResult(6, 7, 8, 9, 10)
	}

	@Test
	fun test_task10() {
		val source = Observable.just(
			"Понедельник",
			"Вторник",
			"Вторник",
			"Среда",
			"Четверг",
			"Пятница",
			"Четверг",
			"Суббота",
			"Суббота",
			"Воскресенье",
		)
		val expected = arrayOf(
			"Понедельник",
			"Вторник",
			"Среда",
			"Четверг",
			"Пятница",
			"Суббота",
			"Воскресенье",
		)
		ObservableOperators.Task10.solve(source)
			.test()
			.assertResult(*expected)
	}

	@Test
	fun test_task11() {
		ObservableOperators.Task11.solve(Observable.just(-1, 0, 5))
			.test()
			.assertResult(0, 1, 6)
	}

	@Test
	fun test_task12() {
		val source = Observable.just(
			Pie(color = "Red", burnt = false),
			Pie(color = "Orange", burnt = true),
			Pie(color = "Yellow", burnt = false),
			Pie(color = "Green", burnt = true),
			Pie(color = "Blue", burnt = true),
		)
		val expected = arrayOf(
			Package(Pie(color = "Red", burnt = false)),
			Package(Pie(color = "Yellow", burnt = false)),
		)
		ObservableOperators.Task12.solve(source)
			.test()
			.assertResult(*expected)
	}

	@Test
	fun test_task13() {
		val expected = arrayOf(
			File(1),
			File(2),
			File(3),
			File(4),
		)
		val uriObservable = Observable.just("uri1", "uri2", "uri3")
		val loader = object : ObservableOperators.Task13.FilesLoader {
			override fun load(uri: String): Observable<File> =
				when (uri) {
					"uri1" -> Observable.just(File(1))
					"uri2" -> Observable.just(File(2), File(3))
					"uri3" -> Observable.just(File(4))
					else   -> throw IllegalArgumentException("Unknown uri")
				}
		}

		ObservableOperators.Task13.solve(uriObservable, loader)
			.test()
			.assertResult(*expected)
	}

	@Test
	fun test_task14() {
		val digits = PublishSubject.create<String>()
		val letters = PublishSubject.create<String>()

		val testObserver = ObservableOperators.Task14.solve(digits, letters)
			.test()

		letters.onNext("z")

		digits.onNext("1")
		digits.onNext("2")
		digits.onNext("3")
		digits.onComplete()

		letters.onNext("a")
		letters.onNext("b")
		letters.onNext("c")
		letters.onComplete()

		testObserver.assertResult("1", "2", "3", "a", "b", "c")
	}

	@Test
	fun test_task15() {
		val digits = PublishSubject.create<String>()
		val letters = PublishSubject.create<String>()

		val testObserver = ObservableOperators.Task15.solve(digits, letters)
			.test()

		digits.onNext("1")
		letters.onNext("a")
		digits.onNext("2")
		letters.onNext("b")
		digits.onNext("3")
		letters.onNext("c")

		digits.onComplete()
		letters.onComplete()

		testObserver.assertResult("1", "a", "2", "b", "3", "c")
	}

	@Test
	fun test_task16() {
		val subscribeCounter = AtomicInteger()

		val first = Observable.just(1, 2, 3)
			.doOnSubscribe { subscribeCounter.incrementAndGet() }

		val second = Observable.just(10, 20, 30)
			.delay(5, TimeUnit.SECONDS)
			.doOnSubscribe { subscribeCounter.incrementAndGet() }

		val testObserver = ObservableOperators.Task16.solve(first, second)
			.test()
			.assertEmpty()

		assertEquals(2, subscribeCounter.get())

		testRule.scheduler.advanceTimeBy(4, TimeUnit.SECONDS)
		testObserver.assertEmpty()

		testRule.scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
		testObserver.assertResult(11, 22, 33)
	}

	@Test
	fun test_task17() {
		val source = Observable.just(0, 1, 2)
			.flatMapSingle { number ->
				Single.timer(number.toLong(), TimeUnit.SECONDS, testRule.scheduler).map { number }
			}

		val testObserver = ObservableOperators.Task17.solve(source)
			.test()
			.assertEmpty()

		testRule.scheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
		testObserver.assertEmpty()

		testRule.scheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
		testObserver.assertValuesOnly(0)

		testRule.scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
		testObserver.assertValuesOnly(0, 1)

		testRule.scheduler.advanceTimeBy(1, TimeUnit.SECONDS)
		testObserver.assertResult(0, 1, 2)
	}

	@Test
	fun test_task18() {
		val source = PublishSubject.create<String>()
		val timeout = 100L

		val testObserver = ObservableOperators.Task18.solve(source, timeout)
			.test()
			.assertEmpty()

		source.onNext("H")

		testRule.scheduler.advanceTimeBy(timeout - 1, TimeUnit.MILLISECONDS)
		testObserver.assertEmpty()

		testRule.scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
		testObserver.assertValuesOnly("H")

		source.onNext("He")

		testRule.scheduler.advanceTimeBy(timeout - 1, TimeUnit.MILLISECONDS)
		testObserver.assertValuesOnly("H")

		source.onNext("Hello")
		testRule.scheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)
		testObserver.assertValuesOnly("H")

		testRule.scheduler.advanceTimeBy(timeout, TimeUnit.MILLISECONDS)
		testObserver.assertValues("H", "Hello")
	}
}