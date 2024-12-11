package com.example.lab7.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.lab7.data.datasource.ContactListDeviceDataSource
import com.example.lab7.ui.MainActivity
import com.example.lab7.data.datasource.MainDb
import com.example.lab7.data.datasource.ContactListLocalDataSource
import com.example.lab7.data.datasource.DeviceContactListDataSource
import com.example.lab7.data.datasource.RoomContactListDataSource
import com.example.lab7.data.repository.ContactListRepositoryImpl
import com.example.lab7.presentation.ContactViewModel
import com.example.lab7.presentation.ContactsListViewModel
import com.example.lab7.presentation.ViewModelFactory
import com.example.lab7.domain.repository.ContactListRepository
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Component(modules = [DataModule::class, RoomModule::class, UiModule::class, DomainModule::class])
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    @Singleton
    fun viewModelsFactory() : ViewModelFactory
    fun inject(activity: MainActivity)
}

@Module
class DataModule{

    @Provides
    @Singleton
    fun provideContactListLocalDataSource(contactDb: MainDb): ContactListLocalDataSource {
        return RoomContactListDataSource(contactDb.getDao())
    }

    @Provides
    @Singleton
    fun provideContactListDeviceDataSource(context: Context): ContactListDeviceDataSource {
        return DeviceContactListDataSource(context)
    }
}

@Module
abstract class DomainModule{

    @Binds
    abstract fun provideContactListRepository(contactListRepositoryImpl: ContactListRepositoryImpl): ContactListRepository
}

@Module
class RoomModule{

    @Provides
    @Singleton
    fun provideDatabase(context : Context) = MainDb.getDb(context)

}

@Module
abstract class UiModule{
    @Binds
    @IntoMap
    @ViewModelKey(ContactsListViewModel::class)
    abstract fun bindContactViewModel(contactViewModel: ContactsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactViewModel::class)
    abstract fun bindContactsViewModel(contactsViewModel: ContactViewModel): ViewModel

}