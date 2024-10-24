package group.beymen.network.ui.outlet

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.model.outletpage.OutletState
import group.beymen.network.data.repository.OutletRepository
import group.beymen.network.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OutletViewModel @Inject constructor(
    private val outletRepository: OutletRepository
) : ViewModel() {

    var outletState by mutableStateOf(OutletState())
        private set

    fun fetchOutletItems(bannerDeviceType: String, code: String) {
        viewModelScope.launch {
            outletState = outletState.copy(isLoading = true)

            val result = outletRepository.getOutletItems(bannerDeviceType, code)

            outletState = when (result) {
                is Resource.Success -> {
                    OutletState(outletItems = result.data?.Result)
                }
                is Resource.Error -> {
                    OutletState(error = result.message ?: "An unexpected error occurred")
                }
                else -> OutletState()
            }
        }
    }
}
