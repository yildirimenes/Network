package group.beymen.network.ui.outlet

import group.beymen.network.data.model.outletpage.OutletModel

data class OutletPageState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val outletItems: List<OutletModel>? = null,
    val error: String? = null
)
