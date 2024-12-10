package group.beymen.network.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import group.beymen.network.data.local.ProductListCache
import group.beymen.network.data.model.productlist.PriceFilter
import group.beymen.network.data.model.productlist.ProductListResponseModel
import group.beymen.network.data.source.remote.ProductService
import group.beymen.network.data.util.NetworkResult
import group.beymen.network.data.util.NetworkUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductListRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val productListCache: ProductListCache,
    @ApplicationContext private val context: Context
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
        val isOnline = NetworkUtil.isOnline(context)
        if (isOnline) {
            try {
                val response = productService.getProductList(
                    categoryId, page, dropListingPageSize, orderOption, minPrice, maxPrice, filters, priceRange
                ).result

                productListCache.saveProducts(categoryId, page, response!!.ProductList)

                emit(NetworkResult.OnSuccess(response))
            } catch (e: Exception) {
                emit(NetworkResult.OnError(e.localizedMessage ?: "Error fetching data"))
            }
        } else {
            val cachedProducts = productListCache.getAllProducts(categoryId)

            if (cachedProducts.isNotEmpty()) {
                val pagedProducts = cachedProducts
                    .drop((page - 1) * dropListingPageSize)
                    .take(dropListingPageSize)

                if (pagedProducts.isNotEmpty()) {
                    emit(
                        NetworkResult.OnSuccess(
                            ProductListResponseModel(
                                bannerLink = "",
                                CategoryId = categoryId,
                                CategoryName = "Cached Products",
                                ContentList = emptyList(),
                                Filters = emptyList(),
                                IsQuickFilterActive = false,
                                OrderingOptions = emptyList(),
                                PriceFilter = PriceFilter(
                                    MaxPrice = pagedProducts.maxOfOrNull { it.ActualPrice.toInt() } ?: 0,
                                    MinPrice = pagedProducts.minOfOrNull { it.ActualPrice.toInt() } ?: 0
                                ),
                                ProductList = pagedProducts,
                                QuickFilter = emptyList(),
                                TotalItemCount = cachedProducts.size,
                                TotalPageCount = (cachedProducts.size + dropListingPageSize - 1) / dropListingPageSize
                            )
                        )
                    )
                } else {
                    emit(NetworkResult.OnError("No cached data for the requested page"))
                }
            } else {
                emit(NetworkResult.OnError("No cached data available"))
            }
        }
    }
}
