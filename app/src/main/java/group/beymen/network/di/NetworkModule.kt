package group.beymen.network.di

import android.util.Log
import group.beymen.network.data.source.remote.HomePageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import group.beymen.network.util.Constants.BASE_URL
import group.beymen.network.data.source.remote.OutletPageService
import group.beymen.network.data.source.remote.ProductDetailService
import group.beymen.network.data.source.remote.ProductService
import okhttp3.Call
import okhttp3.EventListener
import okhttp3.Handshake
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .eventListener(object : EventListener() {
                //SSL Pinning Control
                override fun secureConnectEnd(call: Call, handshake: Handshake?) {
                    Log.d("OkHttp", "Handshake completed: ${handshake?.peerPrincipal?.name}")
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
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
