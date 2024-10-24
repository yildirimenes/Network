package group.beymen.network.data.repository

import group.beymen.network.data.model.homepage.MainPageResponse
import group.beymen.network.util.Resource

interface MainPageRepository {
    suspend fun getMainPage(): Resource<MainPageResponse>
}
