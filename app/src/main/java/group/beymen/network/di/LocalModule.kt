package group.beymen.network.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import group.beymen.network.data.source.local.FavoriteDatabase
import group.beymen.network.data.source.local.FavoriteProductDao
import group.beymen.network.data.model.productlist.ProductCache
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoriteDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteDatabase::class.java,
            "favorite_products_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: FavoriteDatabase): FavoriteProductDao {
        return database.favoriteProductDao()
    }

    @Provides
    @Singleton
    fun provideProductCache(): ProductCache = ProductCache()
}
