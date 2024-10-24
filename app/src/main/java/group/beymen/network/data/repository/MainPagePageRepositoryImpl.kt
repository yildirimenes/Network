package group.beymen.network.data.repository

import group.beymen.network.data.model.homepage.MainPageResponse
import group.beymen.network.data.source.remote.MainPageService
import group.beymen.network.util.Resource
import javax.inject.Inject

class MainPagePageRepositoryImpl @Inject constructor(
    private val mainPageService: MainPageService
) : MainPageRepository {
    override suspend fun getMainPage(): Resource<MainPageResponse> {
        return try {
            val response = mainPageService.getMainPage()
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
