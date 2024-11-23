package group.beymen.network.data.repository

import group.beymen.network.data.model.productdetail.ProductDetailResponse
import group.beymen.network.data.source.remote.ProductDetailService
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val api: ProductDetailService
) : ProductDetailRepository {
    override suspend fun fetchProductDetail(productId: Int): ProductDetailResponse {
        return api.getProductDetail(productId)
    }
}
