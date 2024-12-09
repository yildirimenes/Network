package group.beymen.network.data.source.remote

import group.beymen.network.data.model.BaseResponseModel
import group.beymen.network.data.model.productlist.ProductListResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("mobile2/mbProduct/ProductList")
    suspend fun getProductList(
        @Query("CategoryID") CategoryID: Int,
        @Query("Page") Page: Int,
        @Query("dropListingPageSize") dropListingPageSize: Int,
        @Query("orderOption") orderOption: String?,
        @Query("minPrice") minPrice: Double?,
        @Query("maxPrice") maxPrice: Double?,
        @Query("FilterIDList") filters: String?,
        @Query("fiyat-araligi") priceRange: String?
    ): BaseResponseModel<ProductListResponseModel>
}