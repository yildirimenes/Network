package group.beymen.network.data.repository

import group.beymen.network.data.local.OutletPageCache
import group.beymen.network.data.model.outletpage.OutletResponseModel
import group.beymen.network.data.source.remote.OutletPageService
import group.beymen.network.data.util.Resource
import javax.inject.Inject

class OutletRepositoryImpl @Inject constructor(
    private val outletPageService: OutletPageService,
    private val outletPageCache: OutletPageCache
) : OutletRepository {

    override suspend fun getOutletItems(bannerDeviceType: String, code: String): Resource<OutletResponseModel> {
        val cachedData = outletPageCache.get(bannerDeviceType, code)
        if (cachedData != null) {
            return Resource.Success(cachedData)
        }

        return try {
            val response = outletPageService.getOutletContent(bannerDeviceType, code)
            outletPageCache.save(bannerDeviceType, code, response)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
