package group.beymen.network.data.model.homepage

data class HomePageResponse(
    val Success: Boolean,
    val Message: String?,
    val ErrorMessageCode: Int?,
    val Result: List<HomePageItem>?
)
