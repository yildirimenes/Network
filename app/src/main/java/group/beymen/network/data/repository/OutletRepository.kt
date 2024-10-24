package group.beymen.network.data.repository

import group.beymen.network.data.model.outletpage.OutletPageResponse
import group.beymen.network.util.Resource

interface OutletRepository {
    suspend fun getOutletItems(bannerDeviceType: String, code: String): Resource<OutletPageResponse>
}
