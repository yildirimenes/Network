package group.beymen.network.data.repository

import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.source.remote.HomePageService
import group.beymen.network.data.util.Resource
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(
    private val homePageService: HomePageService
) : HomePageRepository {
    override suspend fun getMainPage(): Resource<HomeResponseModel> {
        return try {
            val response = homePageService.getMainPage()
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.Success(it)
                } ?: Resource.Error("Veri boş döndü")
            } else {
                Resource.Error("Hata: ${response.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("İstek başarısız: ${e.localizedMessage}")
        }
    }
}
