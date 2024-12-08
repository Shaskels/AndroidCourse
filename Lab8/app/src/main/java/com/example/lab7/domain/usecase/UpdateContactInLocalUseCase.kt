package com.example.lab7.domain.usecase

import com.example.lab7.domain.presentation.ContactFromLocal
import com.example.lab7.domain.repository.ContactListRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateContactInLocalUseCase @Inject constructor(private val repository: ContactListRepository) {
    operator fun invoke(contact: ContactFromLocal): Completable {
        val fromLocal = repository.updateContactInList(contact)
        return fromLocal
    }
}