package group.beymen.network.data.repository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import androidx.room.Entity
import androidx.room.PrimaryKey
import group.beymen.network.data.source.local.MainDao
/*
class FavoriteRepository @Inject constructor(private val dao: MainDao) {

    val favoriteProducts: Flow<List<FavoriteProduct>> = dao.getAllFavorites()

    suspend fun addToFavorites(product: FavoriteProduct) {
        dao.addToFavorites(product)
    }

    suspend fun removeFromFavorites(productId: Int) {
        dao.removeFromFavorites(productId)
    }
 */