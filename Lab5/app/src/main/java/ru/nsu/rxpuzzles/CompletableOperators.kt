package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

object CompletableOperators {

    object Task1 {

        /**
         * Вернуть Completable, который моментально успешно завершает работу.
         */
        fun solve(): Completable {
            return Completable.complete();
        }
    }

    object Task2 {

        /**
         * Вернуть Completable, который моментально завершает работу с любой ошибкой.
         */
        fun solve(): Completable{
            val exception = Exception("error!")
            return Completable.error(exception)
        }
    }

    object Task3 {

        /**
         * Преобразовать вызов блокирующей поток операции в Completable.
         */
        fun solve(worker: Worker): Completable {
            return Completable.fromRunnable{ worker.doWork() }
        }

        interface Worker {

            // Этот вызов блокирует поток
            fun doWork()
        }
    }

    object Task4 {

        /**
         * Преобразовать [source] в Completable.
         */
        fun solve(source: Single<String>): Completable {
            return Completable.fromSingle(source)
        }
    }

    object Task5 {

        /**
         * Последовательно выполнить [first], [second] и [third].
         */
        fun solve(first: Completable, second: Completable, third: Completable): Completable {
            return Completable.concat(listOf(first, second,third))
        }
    }

    object Task6 {

        /**
         * Переключить операторы, которые будут идти ниже по цепочке на [scheduler].
         */
        fun solve(source: Completable, scheduler: Scheduler): Completable{
            return  source.observeOn(scheduler)
        }
    }

    object Task7 {

        /**
         * Переключить выполнение всей цепочки на [scheduler].
         */
        fun solve(source: Completable, scheduler: Scheduler): Completable{
            return  source.subscribeOn(scheduler)
        }
    }
}