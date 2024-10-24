package group.beymen.network.di

import group.beymen.network.data.repository.MainPagePageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import group.beymen.network.data.repository.MainPageRepository
import group.beymen.network.data.repository.OutletPageRepositoryImpl
import group.beymen.network.data.repository.OutletRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(repositoryImpl: MainPagePageRepositoryImpl): MainPageRepository

    @Binds
    @Singleton
    abstract fun bindOutletRepository(repositoryImpl: OutletPageRepositoryImpl): OutletRepository
}

