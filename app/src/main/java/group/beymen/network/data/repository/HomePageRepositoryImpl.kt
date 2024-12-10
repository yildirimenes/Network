package group.beymen.network.data.repository

import group.beymen.network.data.local.HomePageCache
import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.source.remote.HomePageService
import group.beymen.network.data.util.Resource
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(
    private val homePageService: HomePageService,
    private val homePageCache: HomePageCache
) : HomePageRepository {

    override suspend fun getMainPage(): Resource<HomeResponseModel> {
        return try {
            val response = homePageService.getMainPage()
            if (response.isSuccessful) {
                val data = response.body() ?: throw Exception("The data returned is empty")
                homePageCache.save(data)
                Resource.Success(data)
            } else {
                throw Exception("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            val cachedData = homePageCache.get()
            if (cachedData != null) {
                Resource.Success(cachedData)
            } else {
                Resource.Error(e.localizedMessage ?: "An error occurred")
            }
        }
    }
}
