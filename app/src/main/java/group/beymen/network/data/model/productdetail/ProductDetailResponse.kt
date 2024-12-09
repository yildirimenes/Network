package group.beymen.network.data.model.productdetail

data class ProductDetailResponse(
    val ErrorMessageCode: Int,
    val Message: String,
    val Success: Boolean,
    val Result: ProductDetailResult?
)

data class ProductDetailResult(
    val Id: Int,
    val StoreId: Int,
    val DisplayName: String,
    val FriendlyUri: String,
    val PageTitle: String?,
    val MetaKeywords: String?,
    val MetaDescription: String?,
    val ExternalSystemCode: String?,
    val VatRate: Int,
    val ActualPrice: Double,
    val LabelPrice: Double,
    val SeasonPrice: Double?,
    val DiscountRate: Int?,
    val DiscountAmount: Double?,
    val LimitOfShowDiscount: Int?,
    val IsDiscountBadgeEnabled: Boolean?,
    val IsLabelPriceActive: Boolean?,
    val IsSeasonPriceActive: Boolean?,
    val MainBrand: String?,
    val Brand: String,
    val Color: String?,
    val Group: String?,
    val GenderCode: String?,
    val Gender: String?,
    val Year: String?,
    val Season: String?,
    val GoodType: String?,
    val StyleNote: String?,
    val ModelSize: String?,
    val SampleSize: String?,
    val ShareUrl: String?,
    val SizeTableUrl: String?,
    val ProductCareUrl: String?,
    val AdditionalDescriptionUrl: String?,
    val AdditionalDescription: String?,
    val IsUseInfoInSeo: Boolean?,
    val IsOnlineExclusive: Boolean?,
    val HasRelatedProduct: Boolean?,
    val HasLowStock: Boolean?,
    val IsOutOfStock: Boolean?,
    val IsSingleSize: Boolean?,
    val VideoUrl: String?,
    val DiscountLabelType: Int?,
    val ParsedProductDetails: List<String>?,
    val Badges: List<Badge>?,
    val Sizes: List<ProductSize>?,
    val Images: List<String>?,
    val Colors: List<ProductColor>?,
    val IsWebSaleForbidden: Boolean?,
    val Breadcrumb: BreadcrumbTree?,
    val ProductPromotion: ProductPromotion?,
    val BreadcrumbModelList: List<Breadcrumb>?
)

data class ProductSize(
    val AttributeOptionId: Int,
    val VariantId: Int,
    val ValueText: String,
    val Barcode: String?,
    val VariantExternalCode: String?,
    val Quantity: Int?,
    val NoStock: Boolean
)

data class ProductPromotion(
    val CampaignId: Int,
    val ShowCampaign: Boolean,
    val ShowPrice: Boolean,
    val PromotedPrice: String,
    val CampaignTitle: String
)

data class ProductColor(
    val ProductId: Int,
    val ColorName: String,
    val ColorCode: String,
    val ImageUrl: String,
    val OwnColor: Boolean,
    val FriendlyUri: String
)

data class Badge(
    val AttributeOptionId: Int,
    val AttributeOptionCode: String,
    val ImageUrl: String
)

data class Breadcrumb(
    val Id: Int,
    val Code: String,
    val DisplayName: String,
    val CategoryUrl: String,
    val CustomUrl: String?,
    val IsNotHyperlink: Boolean?,
    val IsOpenNewPage: Boolean?,
    val IsCustomUrlExist: Boolean?,
    val Href: String?
)

data class BreadcrumbTree(
    val Id: Int,
    val ParentCategoryId: Int?,
    val Code: String,
    val DisplayName: String,
    val FriendlyURI: String,
    val CustomUrl: String?,
    val IsNotHyperlink: Boolean?,
    val IsOpenNewPage: Boolean?,
    val ChildCategory: BreadcrumbTree?
)
