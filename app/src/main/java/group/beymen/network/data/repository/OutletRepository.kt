package group.beymen.network.data.repository

import group.beymen.network.data.model.outletpage.OutletResponseModel
import group.beymen.network.data.util.Resource

interface OutletRepository {
    suspend fun getOutletItems(bannerDeviceType: String, code: String): Resource<OutletResponseModel>
}
