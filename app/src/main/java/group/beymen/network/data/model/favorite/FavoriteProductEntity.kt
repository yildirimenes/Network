package group.beymen.network.data.model.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey val id: Int,
    val displayName: String,
    val labelPrice: Double,
    val promotedPrice: Double?,
    val imageUrls: List<String>,
    val campaignTitle: String?,
    val brandName: String?,
    val discount: Int?,
    val classificationImage: String?
)
