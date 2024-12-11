package com.example.lab7.domain.usecase

import com.example.lab7.presentation.ContactFromLocal
import com.example.lab7.domain.repository.ContactListRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetContactListFromLocalUseCase @Inject constructor(private val repository: ContactListRepository) {
    operator fun invoke(): Observable<List<ContactFromLocal>> {
        val fromLocal = repository.fetchContactList()
        return fromLocal
    }
}