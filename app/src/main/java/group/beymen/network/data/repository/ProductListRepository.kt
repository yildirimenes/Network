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

/*
class ProductListRepository @Inject constructor(
    private val api: ProductService
) {

    suspend fun getProductList(
        categoryId: Int,
        page: Int,
        dropListingPageSize: Int,
        orderOption: String,
        minPrice: Double?,
        maxPrice: Double?,
        filters: String,
        priceRange: String?
    ): Flow<NetworkResult<ProductListResponseModel>> {
        return safeApiCall {
            api.getProductList(categoryId, page, dropListingPageSize, orderOption, minPrice, maxPrice, filters, priceRange)
        }
    }

    // Fetch product by ID
    suspend fun getProductById(productId: Int): Product? {
        return safeApiCall {
            api.getProductDetailsById(productId)
        }.getOrNull() // Adjust based on your error handling
    }
}*/
