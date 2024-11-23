package group.beymen.network.data.model.productdetail

data class ProductDetailResponse(
    val ErrorMessageCode: Int,
    val Message: String,
    val Success: Boolean,
    val Result: ProductDetailResult?
)

data class ProductDetailResult(
    val Id: Int,
    val DisplayName: String,
    val ActualPrice: Double,
    val LabelPrice: Double,
    val Brand: String,
    val Color: String?,
    val Season: String?,
    val Images: List<String>?,
    val Sizes: List<ProductSize>?,
    val ProductPromotion: ProductPromotion?,
    val BreadcrumbModelList: List<Breadcrumb>?
)

data class ProductSize(
    val AttributeOptionId: Int,
    val VariantId: Int,
    val ValueText: String,
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

data class Breadcrumb(
    val Id: Int,
    val Code: String,
    val DisplayName: String,
    val CategoryUrl: String
)
