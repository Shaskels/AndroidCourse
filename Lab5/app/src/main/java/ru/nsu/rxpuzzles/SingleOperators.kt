package ru.nsu.rxpuzzles

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function3

object SingleOperators {

    object Task1 {
        /**
         * Реализовать поток данных вида:
         * "Hello"
         *
         * Успешно завершить передачу данных.
         */
        fun solve(): Single<String> {
            return Single.just("Hello")
        }
    }

    object Task2 {

        /**
         * Установить слушателя инициализации чата.
         * При успешной инициализации успешно завершать поток с передачей имени чата.
         * При фатальной ошибке инициализации завершать поток с ошибкой.
         */
        fun solve(chat: Chat): Single<String> {
            return Single.create { emitter ->
                chat.setInitListener(MessageListener(
                    { m -> emitter.onSuccess(m) },
                    { e -> emitter.onError(e) }
                ))
            }
        }

        interface Chat {

            fun setInitListener(listener: MessageListener)
        }

        class MessageListener(
            val onSuccess: (name: String) -> Unit,
            val onFatalError: (Throwable) -> Unit,
        )
    }

    object Task3 {

        /**
         * Реализовать поток данных, который будет возвращать значение, получаемое из провайдера.
         * Провайдер при каждом вызове возвращает новое уникальное значение.
         * Сделать так, чтобы при повторной подписке возвращалось новое значение.
         */
        fun solve(provider: DataProvider): Single<Int> {
            return Single.fromCallable { provider.get() }
        }

        interface DataProvider {

            fun get(): Int
        }
    }

    object Task4 {

        /**
         * Уменьшить значение из [source] на единицу.
         */
        fun solve(source: Observable<Int>): Observable<Int> {
            return source.map { it - 1 }
        }
    }

    object Task5 {

        /**
         * [api] предоставляет методы для работы с пользователем.
         * create - создает нового пользователя и возвращает id.
         * get - возращает сущность пользователя по указанному id.
         *
         * Необходимо создать пользователя с указанным именем и вернуть сущность User.
         */
        fun solve(name: String, api: UserApi): Single<User> {
            return api.create(name).flatMap { api.get(it) }
        }

        interface UserApi {

            fun create(name: String): Single<Long>
            fun get(id: Long): Single<User>
        }

        data class User(val name: String, val id: Long)
    }

    object Task6 {

        /**
         * Выполнить параллельно запросы id, name, age пользователя и вернуть сущность User.
         * Предполагается, что запросы getId(), getName(), getAge() уже выполняются на разных тредах.
         */
        fun solve(userApi: UserApi): Single<User> {
            val zipper = Function3<Long, String, Int, User> { a, b, c -> User(a, b, c) }
            return Single.zip(userApi.getId(), userApi.getName(), userApi.getAge(), zipper)
        }

        interface UserApi {

            fun getId(): Single<Long>
            fun getName(): Single<String>
            fun getAge(): Single<Int>
        }

        data class User(val id: Long, val name: String, val age: Int)
    }
}