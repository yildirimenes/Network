package group.beymen.network.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.beymen.network.data.source.local.LocalDatabase
import group.beymen.network.data.source.local.FavoriteProductDao
import group.beymen.network.data.source.local.HomePageDao
import group.beymen.network.data.source.local.OutletPageDao
import group.beymen.network.data.source.local.ProductListDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "favorite_products_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: LocalDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }

    @Provides
    @Singleton
    fun provideHomePageCache(database: LocalDatabase): HomePageDao {
        return database.homePageDao()
    }

    @Provides
    @Singleton
    fun provideOutletPageCache(database: LocalDatabase): OutletPageDao {
        return database.outletPageDao()
    }

    @Provides
    @Singleton
    fun provideProductListCache(database: LocalDatabase): ProductListDao {
        return database.productListDao()
    }

}
