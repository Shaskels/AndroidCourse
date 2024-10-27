package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import java.util.concurrent.TimeUnit

object ObservableOperators {

    object Task1 {

        /**
         * Реализовать поток данных вида:
         * Hello
         *
         * Успешно завершить передачу данных.
         */
        fun solve(): Observable<String> {
            return Observable.just("Hello")

        }
    }

    object Task2 {

        /**
         * Реализовать поток данных вида:
         * Понедельник, Вторник, Среда, Четверг, Пятница, Суббота, Воскресенье
         *
         * Успешно завершить передачу данных.
         */
        private val weekdays = listOf(
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
        )

        fun solve(): Observable<String> {
            return Observable.fromIterable(weekdays)
        }
    }

    object Task3 {

        /**
         * Реализовать поток данных вида:
         * 1, 2, 3, 4, 5, ... , 100
         *
         * Успешно завершить передачу данных.
         */
        private val data = 1..100

        fun solve(): Observable<Int> {
            return Observable.fromIterable(data)
        }
    }

    object Task4 {

        /**
         * Реализовать поток данных вида:
         * 5, 4, 3, 2, 1
         *
         * Завершить передачу данных ошибкой c message="Boom".
         */
        private val data = 5 downTo 1

        fun solve(): Observable<Int> {
            val obser1 = Observable.fromIterable(data)
            val obser2 = Observable.error<Int>(Exception("Boom"))
            return Observable.concat(obser1, obser2)
        }
    }

    object Task5 {

        /**
         * Реализовать поток данных из [list].
         *
         * Успешно завершить передачу данных.
         */
        fun solve(list: List<String>): Observable<String> {
            return Observable.fromIterable(list)
        }
    }

    object Task6 {

        /**
         * Установить слушателя и преобразовать сообщения, получемые из чата в поток сообщений.
         * При фатальной ошибке чата завершать поток с ошибкой.
         */
        fun solve(chat: Chat): Observable<String> = TODO()

        interface Chat {

            fun setMessageListener(listener: MessageListener)
        }

        class MessageListener(
            val onMessage: (String) -> Unit,
            val onFatalError: (Throwable) -> Unit,
        )
    }

    object Task7 {

        /**
         * Оставить только нечетные числа из [source].
         */
        fun solve(source: Observable<Int>): Observable<Int> {
            return source.filter{ it % 2 !=0 }
        }
    }

    object Task8 {

        /**
         * Оставить только первые 5 значений из [source].
         */
        fun solve(source: Observable<Int>): Observable<Int> {
            return source.take(5)
        }
    }

    object Task9 {

        /**
         * Проигнорировать первые 5 значений из [source].
         */
        fun solve(source: Observable<Int>): Observable<Int> {
            return source.skip(5)
        }
    }

    object Task10 {

        /**
         * Не пропускать повторяющиеся значения из [source].
         */
        fun solve(source: Observable<String>): Observable<String> {
            return source.distinct()
        }
    }

    object Task11 {

        /**
         * Увеличить каждое значение из [source] на единицу.
         */
        fun solve(source: Observable<Int>): Observable<Int> {
            return source.map { it + 1 }
        }
    }

    object Task12 {

        /**
         * [source] поставляет пироги.
         *
         * Необходимо реализовать поток с упакованными пирогами. Горелые пироги не пропускать.
         */
        fun solve(source: Observable<Pie>): Observable<Package> {
            return source.filter{!it.burnt}.map { Package(it) }
        }

        data class Pie(val color: String, val burnt: Boolean)
        data class Package(val pie: Pie)
    }

    object Task13 {

        /**
         * [uriObservable] поставляет поток uri.
         * [loader] возвращает поток файлов, находящихся по указанному uri.
         *
         * Необходимо реализовать поток файлов полученных по всем uri из [uriObservable]
         */
        fun solve(uriObservable: Observable<String>, loader: FilesLoader): Observable<File> {
            return uriObservable.flatMap { loader.load(it) }
        }

        interface FilesLoader {

            fun load(uri: String): Observable<File>
        }

        data class File(val data: Int)
    }

    object Task14 {

        /**
         * При входных данных:
         * [digits] - "1", "2", "3"
         * [letters] - "a", "b", "c"
         *
         * На выходе ожидаются сначала все значения из [digits] и следом все значения из [letters]:
         * "1", "2", "3", "a", "b", "c"
         */
        fun solve(digits: Observable<String>, letters: Observable<String>): Observable<String> {
            return Observable.concat(listOf(digits, letters))
        }
    }

    object Task15 {

        /**
         * При входных данных:
         * [digits] - "1", "2", "3"
         * [letters] - "a", "b", "c"
         *
         * На выходе ожидается объединенный поток из значений [digits] и [letters].
         * Порядок будет зависить от того с какой задержкой будут эмитить [digits] и [letters].
         * Пример выходного потока: "1", "a", "2", "b", "3", "c"
         */
        fun solve(digits: Observable<String>, letters: Observable<String>): Observable<String> {
            return Observable.merge(listOf(digits,letters))
        }
    }

    object Task16 {

        /**
         * При входных данных:
         * [first] - "1", "2", "3"
         * [second] - "10", "20", "30"
         *
         * На выходе ожидается поток с попарными суммами значений [first] и [second]:
         * "11", "22", "33"
         */
        fun solve(first: Observable<Int>, second: Observable<Int>): Observable<Int> {
            val zipper = BiFunction<Int, Int, Int>{a,b -> a+b}
            return Observable.zip(first, second, zipper)
        }
    }

    object Task17 {

        /**
         * Выполнить задержку выпуска каждого значения из [source] на 1 секунду.
         */
        fun solve(source: Observable<Int>): Observable<Int> {
            return source.delay(1, TimeUnit.SECONDS)
        }
    }

    object Task18 {

        /**
         * Вернуть Observable, который эмитит значение из [source] только,
         * если с момента выпуска предыдущего значения прошло [timeout] милисекунд.
         */
        fun solve(source: Observable<String>, timeout: Long): Observable<String> {
            return source.debounce(timeout, TimeUnit.MILLISECONDS)
        }
    }
}