package group.beymen.network.data.model.outletpage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outlet_page")
data class OutletPageEntity(
    @PrimaryKey val id: String,
    val bannerDeviceType: String,
    val data: String
)
