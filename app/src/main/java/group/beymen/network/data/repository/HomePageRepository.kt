package group.beymen.network.data.repository

import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.util.Resource

interface HomePageRepository {
    suspend fun getMainPage(): Resource<HomeResponseModel>
}
