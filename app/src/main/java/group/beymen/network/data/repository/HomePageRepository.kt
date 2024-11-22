package group.beymen.network.data.repository

import group.beymen.network.data.model.homepage.HomePageResponse
import group.beymen.network.util.Resource

interface HomePageRepository {
    suspend fun getMainPage(): Resource<HomePageResponse>
}
