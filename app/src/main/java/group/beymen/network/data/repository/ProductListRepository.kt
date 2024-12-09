package group.beymen.network.data.repository

import group.beymen.network.data.model.productlist.ProductListResponseModel
import group.beymen.network.data.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ProductListRepository {
    fun getProductList(
        categoryId: Int,
        page: Int,
        dropListingPageSize: Int,
        orderOption: String,
        minPrice: Double?,
        maxPrice: Double?,
        filters: String,
        priceRange: String?
    ): Flow<NetworkResult<ProductListResponseModel>>
}