package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

object MaybeOperators {

    object Task1 {

        /**
         * Вернуть пустой Maybe, который мгновенно завершит работу.
         */
        fun solve(): Maybe<String> {
            return Maybe.empty()
        }
    }

    object Task2 {

        /**
         * Преобразовать результат получаемый из [worker] в Maybe поток.
         */
        fun solve(worker: Worker): Maybe<String> {
            return Maybe.fromAction { worker.setResultListener { s-> println(s) }}
        }

        interface Worker {

            fun setResultListener(listener: (String?) -> Unit)
        }
    }

    object Task3 {

        /**
         * Завершать поток, если в [source] будет отрицательное число.
         * Возвращать значение, если оно будет положительным.
         */
        fun solve(source: Single<Int>): Maybe<Int> {
            return source.flatMapMaybe { if (it > 0) Maybe.just(it) else Maybe.empty() }
        }
    }

    object Task4 {

        /**
         * Если [first] поток не вернет значение, то переключить на [second] поток.
         */
        fun solve(first: Maybe<Int>, second: Single<Int>): Single<Int> {
            return first.switchIfEmpty(second)
        }
    }
}