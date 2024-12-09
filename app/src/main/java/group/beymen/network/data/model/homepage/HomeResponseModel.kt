package group.beymen.network.data.model.homepage

data class HomeResponseModel(
    val Success: Boolean,
    val Message: String?,
    val ErrorMessageCode: Int?,
    val Result: List<HomeItemModel>?
)
