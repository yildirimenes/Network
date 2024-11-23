package group.beymen.network.di

import group.beymen.network.data.source.remote.HomePageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import group.beymen.network.common.Constants.BASE_URL
import group.beymen.network.data.source.remote.OutletPageService
import group.beymen.network.data.source.remote.ProductDetailService
import group.beymen.network.data.source.remote.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideMainService(retrofit: Retrofit): HomePageService {
        return retrofit.create(HomePageService::class.java)
    }

    @Provides
    @Singleton
    fun provideOutletService(retrofit: Retrofit): OutletPageService {
        return retrofit.create(OutletPageService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductDetailService(retrofit: Retrofit): ProductDetailService {
        return retrofit.create(ProductDetailService::class.java)
    }

}
