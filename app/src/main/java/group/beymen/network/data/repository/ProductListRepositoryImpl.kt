package group.beymen.network.data.repository

import group.beymen.network.data.model.productlist.ProductListResponseModel
import group.beymen.network.data.source.remote.ProductService
import group.beymen.network.util.NetworkResult
import group.beymen.network.util.sendRequest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val productService: ProductService,
) : ProductListRepository {
    override fun getProductList(
        categoryId: Int,
        page: Int,
        dropListingPageSize: Int,
        orderOption: String,
        minPrice: Double?,
        maxPrice: Double?,
        filters: String,
        priceRange: String?
    ): Flow<NetworkResult<ProductListResponseModel>> = sendRequest {
        productService.getProductList(
            categoryId,
            page,
            dropListingPageSize,
            orderOption,
            minPrice,
            maxPrice,
            filters,
            priceRange
        )
    }
}