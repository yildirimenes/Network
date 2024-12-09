package group.beymen.network.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.model.favorite.FavoriteProductTypeConverter

@Database(entities = [FavoriteProductEntity::class], version = 2)
@TypeConverters(FavoriteProductTypeConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
}