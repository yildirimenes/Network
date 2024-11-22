package group.beymen.network.di

import group.beymen.network.data.repository.HomePageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import group.beymen.network.data.repository.HomePageRepository
import group.beymen.network.data.repository.OutletPageRepositoryImpl
import group.beymen.network.data.repository.OutletRepository
import group.beymen.network.data.repository.ProductListRepository
import group.beymen.network.data.repository.ProductListRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMainRepository(repositoryImpl: HomePageRepositoryImpl): HomePageRepository

    @Binds
    @Singleton
    abstract fun bindOutletRepository(repositoryImpl: OutletPageRepositoryImpl): OutletRepository

    @Binds
    @Singleton
    abstract fun bindProductListRepository(repositoryImpl: ProductListRepositoryImpl): ProductListRepository

}

