package ru.nsu.dependencyinjectionpractice

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringDataSource
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringLocalDataSource
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringRemoteDataSource
import ru.nsu.dependencyinjectionpractice.data.repository.SampleStringRepositoryImpl
import ru.nsu.dependencyinjectionpractice.domain.repository.SampleStringRepository
import ru.nsu.dependencyinjectionpractice.presentation.MainViewModel
import ru.nsu.dependencyinjectionpractice.ui.MainActivity
import javax.inject.Named
import javax.inject.Singleton

@Component(modules = [UiModule::class])
@Singleton
interface AppComponent{
    fun inject(activity: MainActivity)
}

@Module
class DataModule{

    @Provides
    @Named("Local")
    fun provideSampleStringLocalDataSource(): SampleStringDataSource{
        return SampleStringLocalDataSource()
    }

    @Provides
    @Named("Remote")
    fun provideSampleStringRemoteDataSource(): SampleStringDataSource{
        return SampleStringRemoteDataSource()
    }
}

@Module(includes = [DataModule::class])
interface DomainModule{

    @Suppress("FunctionName")
    @Binds
    @Singleton
    fun bindSampleStringRepositoryImpl_to_SampleStringRepository(
        sampleStringRepositoryImpl: SampleStringRepositoryImpl
    ): SampleStringRepository
}

@Module(includes = [DomainModule::class])
interface PresentationModule{
    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    fun provideMainViewModel(mainViewModel: MainViewModel) : ViewModel

}

@Module(includes = [PresentationModule::class])
class UiModule{


}