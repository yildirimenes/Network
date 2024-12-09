package group.beymen.network.data.repository

import group.beymen.network.data.model.favorite.FavoriteProductEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getFavorites(): Flow<List<FavoriteProductEntity>>
    suspend fun addFavorite(product: FavoriteProductEntity)
    suspend fun removeFavorite(product: FavoriteProductEntity)
    suspend fun removeFavoriteById(id: Int)
}
