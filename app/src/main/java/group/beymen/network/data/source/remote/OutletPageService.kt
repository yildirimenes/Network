package group.beymen.network.data.source.remote

import group.beymen.network.data.model.outletpage.OutletPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OutletPageService {
    @GET("/mobile2/mbContent/GetContentTemplate")
    suspend fun getOutletContent(
        @Query("bannerDeviceType") bannerDeviceType: String,
        @Query("code") code: String
    ): OutletPageResponse
}