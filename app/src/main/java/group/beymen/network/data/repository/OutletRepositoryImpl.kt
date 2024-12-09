package group.beymen.network.data.repository

import group.beymen.network.data.model.outletpage.OutletResponseModel
import group.beymen.network.data.source.remote.OutletPageService
import group.beymen.network.data.util.safeApiCall
import group.beymen.network.data.util.Resource
import javax.inject.Inject

class OutletRepositoryImpl @Inject constructor(
    private val outletPageService: OutletPageService
) : OutletRepository {

    override suspend fun getOutletItems(bannerDeviceType: String, code: String): Resource<OutletResponseModel> {
        return safeApiCall {
            outletPageService.getOutletContent(bannerDeviceType, code)
        }
    }
}

