package group.beymen.network.data.model.productlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val categoryId: Int,
    val page: Int,
    val orderIndex: Int,
    val actualPrice: Double,
    val actualPriceToShowOnScreen: Double,
    val brandName: String,
    val classifications: String?,
    val discount: Int?,
    val discountLabelType: Int,
    val isDiscountBadgeEnabled: Boolean?,
    val displayName: String,
    val estimatedSupplyDate: String?,
    val externalSystemCode: String?,
    val firstProductImageURL: String,
    val isHero: Boolean,
    val isLabelPriceActive: Boolean,
    val isSeasonPriceActive: Boolean,
    val isStrikeThroughPriceExists: Boolean,
    val labelPrice: Double,
    val mediaList: String?,
    val productType: String,
    val seasonPrice: Double,
    val strikeThroughPriceToShowOnScreen: Double,
    val totalDiscount: String?,
    val variantWithStockList: String?,
    val otherProductImages: String?,
    val productPromotion: String?
)

