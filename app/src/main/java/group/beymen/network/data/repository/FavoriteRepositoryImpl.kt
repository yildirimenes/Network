package group.beymen.network.data.repository

import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.source.local.FavoriteProductDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteProductDao
) : FavoriteRepository {
    override fun getFavorites(): Flow<List<FavoriteProductEntity>> = dao.getAllFavorites()

    override suspend fun addFavorite(product: FavoriteProductEntity) = dao.addFavorite(product)

    override suspend fun removeFavorite(product: FavoriteProductEntity) = dao.removeFavorite(product)

    override suspend fun removeFavoriteById(id: Int) = dao.removeFavoriteById(id)
}
