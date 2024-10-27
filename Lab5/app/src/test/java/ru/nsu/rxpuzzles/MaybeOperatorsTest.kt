package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import ru.nsu.rxpuzzles.MaybeOperators

class MaybeOperatorsTest {

	@Test
	fun test_task1() {
		MaybeOperators.Task1.solve()
			.test()
			.assertResult()
	}

	@Test
	fun test_task2() {
		val worker = object : MaybeOperators.Task2.Worker {
			var listener: ((String?) -> Unit)? = null

			override fun setResultListener(listener: (String?) -> Unit) {
				this.listener = listener
			}
		}

		val emptyMaybe = MaybeOperators.Task2.solve(worker).test()
		worker.listener?.invoke(null)
		emptyMaybe.assertResult()

		val notEmptyMaybe = MaybeOperators.Task2.solve(worker).test()
		worker.listener?.invoke("value")
		notEmptyMaybe.assertResult("value")
	}

	@Test
	fun test_task3() {
		MaybeOperators.Task3.solve(Single.just(-5))
			.test()
			.assertResult()

		MaybeOperators.Task3.solve(Single.just(10))
			.test()
			.assertResult(10)
	}

	@Test
	fun test_task4() {
		MaybeOperators.Task4.solve(Maybe.just(1), Single.just(2))
			.test()
			.assertResult(1)

		MaybeOperators.Task4.solve(Maybe.empty(), Single.just(2))
			.test()
			.assertResult(2)
	}
}