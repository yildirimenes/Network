package group.beymen.network.data.model.homepage

data class HomePageItem(
    val Code: String?,
    val Duration: Int?,
    val ItemList: List<HomePageContent>?,
    val ContentItemList: List<HomePageContent>?,
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
