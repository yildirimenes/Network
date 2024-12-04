package group.beymen.network.data.source.remote

import group.beymen.network.data.model.homepage.HomeResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface HomePageService {
    @GET("mbContent/GetMainPage")
    suspend fun getMainPage(): Response<HomeResponseModel>
}
