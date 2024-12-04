package group.beymen.network.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.model.favorite.FavoriteProductTypeConverters

@Database(entities = [FavoriteProductEntity::class], version = 1)
@TypeConverters(FavoriteProductTypeConverters::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
}