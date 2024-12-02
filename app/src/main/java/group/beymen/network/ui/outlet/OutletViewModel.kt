package group.beymen.network.ui.outlet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.repository.OutletRepository
import group.beymen.network.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutletViewModel @Inject constructor(
    private val outletRepository: OutletRepository
) : ViewModel() {

    var outletPageState by mutableStateOf(OutletPageState())
        private set

    private var lastBannerDeviceType: String = ""
    private var lastCode: String = ""

    fun fetchOutletItems(bannerDeviceType: String = lastBannerDeviceType, code: String = lastCode) {
        lastBannerDeviceType = bannerDeviceType
        lastCode = code

        viewModelScope.launch {
            outletPageState = outletPageState.copy(isLoading = true)

            val result = outletRepository.getOutletItems(bannerDeviceType, code)

            outletPageState = when (result) {
                is Resource.Success -> {
                    OutletPageState(outletItems = result.data?.Result)
                }
                is Resource.Error -> {
                    OutletPageState(error = result.message ?: "An unexpected error occurred")
                }
                else -> OutletPageState()
            }
        }
    }
}
