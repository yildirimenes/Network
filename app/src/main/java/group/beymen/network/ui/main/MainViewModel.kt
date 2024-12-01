package group.beymen.network.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import group.beymen.network.util.RootUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(){
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _isRooted = MutableStateFlow(false)
    val isRooted = _isRooted.asStateFlow()

    fun rootedController() {
        viewModelScope.launch {
            _isRooted.value = RootUtil.isDeviceRooted
            _isLoading.value = false
        }
    }
}
