package group.beymen.network.ui.outlet

import group.beymen.network.data.model.outletpage.OutletPageModel

data class OutletPageState(
    val isLoading: Boolean = false,
    val outletItems: List<OutletPageModel>? = null,
    val error: String? = null
)
