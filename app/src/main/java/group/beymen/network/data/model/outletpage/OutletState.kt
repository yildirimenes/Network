package group.beymen.network.data.model.outletpage

data class OutletState(
    val isLoading: Boolean = false,
    val outletItems: List<OutletPageItem>? = null,
    val error: String? = null
)
