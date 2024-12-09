package group.beymen.network.data.repository

import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.source.remote.HomePageService
import group.beymen.network.data.util.Resource
import group.beymen.network.data.util.safeApiCall
import javax.inject.Inject

class HomePageRepositoryImpl @Inject constructor(
    private val homePageService: HomePageService
) : HomePageRepository {
    override suspend fun getMainPage(): Resource<HomeResponseModel> = safeApiCall {
        val response = homePageService.getMainPage()
        if (response.isSuccessful) {
            response.body() ?: throw Exception("The data returned is empty")
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}
