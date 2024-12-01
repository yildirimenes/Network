package group.beymen.network.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import group.beymen.network.data.model.favorite.FavoriteProductEntity

@Database(entities = [FavoriteProductEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun favoriteProductDao(): FavoriteProductDao
}