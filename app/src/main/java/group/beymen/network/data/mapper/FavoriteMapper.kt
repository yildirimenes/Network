package group.beymen.network.data.mapper

import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.model.productlist.Classification
import group.beymen.network.data.model.productlist.Media
import group.beymen.network.data.model.productlist.Product
import group.beymen.network.data.model.productlist.ProductPromotion

fun Product.toFavoriteProductEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        id = this.ID,
        displayName = this.DisplayName,
        labelPrice = this.LabelPrice,
        promotedPrice = this.ProductPromotion?.PromotedPrice?.toDoubleOrNull(),
        imageUrl = this.MediaList?.firstOrNull()?.ResizedUrlPath,
        campaignTitle = this.ProductPromotion?.CampaignTitle,
        brandName = this.BrandName,
        discount = this.Discount,
        classificationImage = this.Classifications.firstOrNull()?.ImageUrl
    )
}

fun FavoriteProductEntity.toProduct(): Product {
    return Product(
        ActualPrice = this.labelPrice,
        ActualPriceToShowOnScreen = this.labelPrice,
        BrandName = this.brandName ?: "",
        Classifications = listOfNotNull(
            this.classificationImage?.let { Classification(ImageUrl = it, ValueText = "") }
        ),
        Discount = this.discount,
        DiscountLabelType = 0,
        IsDiscountBadgeEnabled = false,
        DisplayName = this.displayName,
        EstimatedSupplyDate = null,
        ExternalSystemCode = null.toString(),
        FirstProductImageURL = this.imageUrl ?: "",
        ID = this.id,
        IsHero = false,
        IsLabelPriceActive = false,
        IsSeasonPriceActive = false,
        IsStrikeThroughPriceExists = false,
        LabelPrice = this.labelPrice,
        MediaList = listOfNotNull(
            this.imageUrl?.let { Media(ID = 0, DisplayOrder = 0, ResizedUrlPath = it) }
        ),
        ProductType = "",
        SeasonPrice = this.labelPrice,
        StrikeThroughPriceToShowOnScreen = 0.0,
        TotalDiscount = null,
        VariantWithStockList = emptyList(),
        OtherProductImages = null,
        ProductPromotion = this.promotedPrice?.let {
            ProductPromotion(
                CampaignTitle = this.campaignTitle ?: "",
                PromotedPrice = it.toString(),
                CampaignId = 0,
                ShowCampaign = false
            )
        }
    )
}
