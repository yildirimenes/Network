package group.beymen.network.data.repository

import group.beymen.network.data.model.productdetail.ProductDetailResponse

interface ProductDetailRepository {
    suspend fun fetchProductDetail(productId: Int): ProductDetailResponse
}
