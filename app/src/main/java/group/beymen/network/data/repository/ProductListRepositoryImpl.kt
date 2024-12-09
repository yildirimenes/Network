package group.beymen.network.data.repository

import group.beymen.network.data.model.productlist.PriceFilter
import group.beymen.network.data.model.productlist.ProductListResponseModel
import group.beymen.network.data.source.remote.ProductService
import group.beymen.network.data.util.safeApiCall
import group.beymen.network.data.util.NetworkResult
import group.beymen.network.data.model.productlist.ProductCache
import group.beymen.network.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productCache: ProductCache
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
    ): Flow<NetworkResult<ProductListResponseModel>> = flow {
        val cachedProducts = productCache.get(page)
        if (cachedProducts != null) {
            emit(
                NetworkResult.OnSuccess(
                    ProductListResponseModel(
                        bannerLink = "",
                        CategoryId = 0,
                        CategoryName = "Cache Category",
                        ContentList = emptyList(),
                        Filters = emptyList(),
                        IsQuickFilterActive = false,
                        OrderingOptions = emptyList(),
                        PriceFilter = PriceFilter(0, 0),
                        ProductList = cachedProducts,
                        QuickFilter = emptyList(),
                        TotalItemCount = cachedProducts.size,
                        TotalPageCount = 1
                    )
                )
            )
            return@flow
        }

        val result = safeApiCall {
            productService.getProductList(
                categoryId, page, dropListingPageSize, orderOption, minPrice, maxPrice, filters, priceRange
            )
        }

        val networkResult = when (result) {
            is Resource.Success -> {
                val productListResponse = result.data?.result ?: ProductListResponseModel(
                    bannerLink = "",
                    CategoryId = 0,
                    CategoryName = "",
                    ContentList = emptyList(),
                    Filters = emptyList(),
                    IsQuickFilterActive = false,
                    OrderingOptions = emptyList(),
                    PriceFilter = PriceFilter(0, 0),
                    ProductList = emptyList(),
                    QuickFilter = emptyList(),
                    TotalItemCount = 0,
                    TotalPageCount = 0
                )

                productCache.put(page, productListResponse.ProductList)
                prefetchNextPage(
                    categoryId, page + 1, dropListingPageSize, orderOption, minPrice, maxPrice, filters, priceRange
                )

                NetworkResult.OnSuccess(productListResponse)
            }
            is Resource.Error -> NetworkResult.OnError(result.message)
            is Resource.Loading -> NetworkResult.OnLoading
        }

        emit(networkResult)
    }

    private suspend fun prefetchNextPage(
        categoryId: Int,
        page: Int,
        dropListingPageSize: Int,
        orderOption: String,
        minPrice: Double?,
        maxPrice: Double?,
        filters: String,
        priceRange: String?
    ) {
        if (productCache.get(page) != null) return
        val result = safeApiCall {
            productService.getProductList(
                categoryId, page, dropListingPageSize, orderOption, minPrice, maxPrice, filters, priceRange
            )
        }
        if (result is Resource.Success) {
            val productList = result.data?.result?.ProductList ?: emptyList()
            productCache.put(page, productList.map { product ->
                product.copy(
                    MediaList = product.MediaList?.map { it.copy() },
                    OtherProductImages = product.OtherProductImages?.map { it.copy() }
                )
            })
        }
    }
}


// Not Cache Version
/*
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
}*/