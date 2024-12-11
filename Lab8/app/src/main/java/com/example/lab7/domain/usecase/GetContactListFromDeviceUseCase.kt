package com.example.lab7.domain.usecase

import com.example.lab7.domain.presentation.ContactFromDevice
import com.example.lab7.domain.repository.ContactListRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetContactListFromDeviceUseCase @Inject constructor(private val repository: ContactListRepository) {
    operator fun invoke(): Observable<List<ContactFromDevice>> {
        val fromDevice =  repository.fetchContactListFromDevice()
        return fromDevice
    }
}