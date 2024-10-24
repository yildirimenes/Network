package group.beymen.network.data.source.remote

import group.beymen.network.data.model.mainpage.MainPageResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainPageService {
    @GET("mbContent/GetMainPage")
    suspend fun getMainPage(): Response<MainPageResponse>
}
