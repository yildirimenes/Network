package group.beymen.network.data.model.homepage

data class MainPageResponse(
    val Success: Boolean,
    val Message: String?,
    val ErrorMessageCode: Int?,
    val Result: List<MainPageItem>?
)
