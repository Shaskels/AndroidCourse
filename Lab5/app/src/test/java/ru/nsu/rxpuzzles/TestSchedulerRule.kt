package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Это правило подменяет всех шедулеры на тестовый.
 */
class TestSchedulerRule(val scheduler: TestScheduler = TestScheduler()) : TestRule {

	override fun apply(base: Statement, d: Description): Statement =
		object : Statement() {
			override fun evaluate() {
				RxJavaPlugins.setIoSchedulerHandler { scheduler }
				RxJavaPlugins.setComputationSchedulerHandler { scheduler }
				RxJavaPlugins.setNewThreadSchedulerHandler { scheduler }
				RxJavaPlugins.setSingleSchedulerHandler { scheduler }

				try {
					base.evaluate()
				} finally {
					RxJavaPlugins.reset()
				}
			}
		}
}