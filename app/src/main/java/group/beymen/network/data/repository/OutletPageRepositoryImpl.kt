package group.beymen.network.data.repository

import group.beymen.network.data.model.outletpage.OutletPageResponse
import group.beymen.network.data.source.remote.OutletPageService
import group.beymen.network.data.source.remote.safeApiCall
import group.beymen.network.util.Resource
import javax.inject.Inject

class OutletPageRepositoryImpl @Inject constructor(
    private val outletPageService: OutletPageService
) : OutletRepository {

    override suspend fun getOutletItems(bannerDeviceType: String, code: String): Resource<OutletPageResponse> {
        return safeApiCall {
            outletPageService.getOutletContent(bannerDeviceType, code)
        }
    }
}

