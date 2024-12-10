package group.beymen.network.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import group.beymen.network.data.model.productlist.ProductEntity

@Dao
interface ProductListDao {
    @Query("SELECT * FROM products WHERE categoryId = :categoryId ORDER BY orderIndex ASC")
    suspend fun getAllProducts(categoryId: Int): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductEntity>)

    @Query("DELETE FROM products WHERE categoryId = :categoryId")
    suspend fun clearProductsByCategory(categoryId: Int)
}


