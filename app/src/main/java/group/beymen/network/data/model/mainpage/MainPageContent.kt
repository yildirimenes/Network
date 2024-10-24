package group.beymen.network.data.model.mainpage

data class MainPageContent(
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
