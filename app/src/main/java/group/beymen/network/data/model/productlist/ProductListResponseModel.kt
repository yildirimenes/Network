package group.beymen.network.data.model.productlist

data class ProductListResponseModel(
    val bannerLink: String,
    val CategoryId: Int,
    val CategoryName: String,
    val ContentList: List<Content>,
    val Filters: List<Filter>,
    val IsQuickFilterActive: Boolean,
    val OrderingOptions: List<OrderingOption>,
    val PriceFilter: PriceFilter,
    val ProductList: List<Product>,
    val QuickFilter: List<QuickFilter>,
    val TotalItemCount: Int,
    val TotalPageCount: Int
)

data class Content(
    val Body: String,
    val ContentType: String,
    val Title: String
)

data class Filter(
    val AttributeDisplayType: Any,
    val Attributes: List<Attribute>?,
    val CodeName: String,
    val DisplayName: String
)

data class OrderingOption(
    val Description: String,
    val IsSelected: Boolean,
    val OrderingOption: String
)

data class PriceFilter(
    val MaxPrice: Int,
    val MinPrice: Int
)

data class Product(
    val ActualPrice: Double,
    val ActualPriceToShowOnScreen: Double,
    val BrandName: String,
    val Classifications: List<Classification>,
    val Discount: Int?,
    val DiscountLabelType: Int,
    val IsDiscountBadgeEnabled:Boolean?,
    val DisplayName: String,
    val EstimatedSupplyDate: Any?,
    val ExternalSystemCode: String?,
    val FirstProductImageURL: String,
    val ID: Int,
    val IsHero: Boolean,
    val IsLabelPriceActive: Boolean,
    val IsSeasonPriceActive: Boolean,
    val IsStrikeThroughPriceExists: Boolean,
    val LabelPrice: Double,
    val MediaList: List<Media>?,
    val ProductType: String,
    val SeasonPrice: Double,
    val StrikeThroughPriceToShowOnScreen: Double,
    val TotalDiscount: Any?,
    val VariantWithStockList: List<Stock>,
    val OtherProductImages : List<OtherProductImages>?,
    val ProductPromotion: ProductPromotion?
)

data class ProductPromotion(
    val PromotedPrice: String,
    val CampaignId: Int,
    val ShowCampaign: Boolean,
    val CampaignTitle: String
)

data class QuickFilter(
    val DisplayName: String,
    val Id: Int,
    val IsSelected: Boolean,
    val URLPath: String
)

data class Attribute(
    val DisplayName: String,
    val ID: Int,
    val ImageUrl: String,
    val IsChecked: Boolean,
    val IsDisabled: Boolean,
    val ValueText: String
)

data class Classification(
    val ImageUrl: String,
    val ValueText: String
)

data class Media(
    val ID: Int,
    val DisplayOrder: Int,
    val ResizedUrlPath: String
)

data class Stock(
    val ExternalSystemCode: String,
    val IsSelected: Boolean,
    val LastTwoStocks: Boolean,
    val StockExists: Boolean,
    val ValueText: String,
    val VariantId: Int
)

data class OtherProductImages(
    val ProductId: Int,
    val Style: String?,
    val ColorName: String,
    val CdnPath: String,
    val Id: Int,
    val MediaFileId: Int,
    val MediaFormat: String?,
    val SizeId: Int,
    val DisplayOrder: Int,
    val Code: String?,
    val Height: Int,
    val Width: Int
)
