package group.beymen.network.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.local.FavoriteProductTypeConverter
import group.beymen.network.data.model.outletpage.OutletPageEntity
import group.beymen.network.data.model.productlist.ProductEntity
import group.beymen.network.ui.homepage.HomePageEntity

@Database(
    entities = [
        FavoriteProductEntity::class,
        HomePageEntity::class,
        OutletPageEntity::class,
        ProductEntity::class
    ],
    version = 6,
    exportSchema = true
)
@TypeConverters(FavoriteProductTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favoriteProductDao(): FavoriteProductDao

    abstract fun homePageDao(): HomePageDao

    abstract fun outletPageDao(): OutletPageDao

    abstract fun productListDao(): ProductListDao
}