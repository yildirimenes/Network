package group.beymen.network.data.source.remote

import group.beymen.network.data.model.productdetail.ProductDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductDetailService {
    @GET("mobile2/mbProduct/ProductDetailv2")
    suspend fun getProductDetail(
        @Query("productID") productId: Int
    ): ProductDetailResponse

}