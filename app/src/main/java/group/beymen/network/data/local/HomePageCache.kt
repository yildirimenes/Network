package group.beymen.network.data.local

import com.google.gson.Gson
import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.source.local.HomePageDao
import group.beymen.network.ui.homepage.HomePageEntity
import javax.inject.Inject

class HomePageCache @Inject constructor(
    private val homePageDao: HomePageDao
) {

    private var cache: HomeResponseModel? = null

    suspend fun save(homeResponse: HomeResponseModel) {
        cache = homeResponse
        homePageDao.insert(HomePageEntity(data = Gson().toJson(homeResponse)))
    }

    suspend fun get(): HomeResponseModel? {
        if (cache != null) return cache

        val entity = homePageDao.getHomePage() ?: return null
        val cachedData = Gson().fromJson(entity.data, HomeResponseModel::class.java)
        cache = cachedData
        return cachedData
    }

    suspend fun clear() {
        cache = null
        homePageDao.clear()
    }
}
