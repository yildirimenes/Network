package group.beymen.network.data.local

import com.google.gson.Gson
import group.beymen.network.data.model.outletpage.OutletPageEntity
import group.beymen.network.data.model.outletpage.OutletResponseModel
import group.beymen.network.data.source.local.OutletPageDao
import javax.inject.Inject

class OutletPageCache @Inject constructor(
    private val outletPageDao: OutletPageDao
) {
    private var cache: MutableMap<String, OutletResponseModel> = mutableMapOf()

    suspend fun save(bannerDeviceType: String, code: String, outletResponse: OutletResponseModel) {
        val id = generateId(bannerDeviceType, code)
        cache[id] = outletResponse

        outletPageDao.insert(
            OutletPageEntity(
                id = id,
                bannerDeviceType = bannerDeviceType,
                data = Gson().toJson(outletResponse)
            )
        )
    }

    suspend fun get(bannerDeviceType: String, code: String): OutletResponseModel? {
        val id = generateId(bannerDeviceType, code)

        if (cache.containsKey(id)) {
            return cache[id]
        }

        val entity = outletPageDao.getOutletPage(id) ?: return null
        val cachedData = Gson().fromJson(entity.data, OutletResponseModel::class.java)
        cache[id] = cachedData
        return cachedData
    }

    suspend fun clear() {
        cache.clear()
        outletPageDao.clear()
    }

    private fun generateId(bannerDeviceType: String, code: String): String {
        return "$bannerDeviceType-$code"
    }
}
