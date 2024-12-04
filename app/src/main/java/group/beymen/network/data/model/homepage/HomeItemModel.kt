package group.beymen.network.data.model.homepage

data class HomeItemModel(
    val Code: String?,
    val Duration: Int?,
    val ItemList: List<HomeContent>?,
    val ContentItemList: List<HomeContent>?,
    val Type: String?,
    val ImageUrl: String?,
    val VideoUrl: String?,
    val IsVideo: Boolean?,
    val IsOpenNewPage: Boolean?,
    val Link: String?,
    val Title: String?,
    val HasCategory: Boolean?,
    val CategoryID: String?,
    val IsProductDetail: Boolean?,
    val ProductId: String?,
    val BannerPlace: String?
)
