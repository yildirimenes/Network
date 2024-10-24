package group.beymen.network.ui.homepage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.model.homepage.MainPageResponse
import group.beymen.network.data.repository.MainPageRepository
import group.beymen.network.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainPageRepository: MainPageRepository
) : ViewModel() {

    private val _mainPageState = mutableStateOf<Resource<MainPageResponse>>(Resource.Loading())
    val mainPageState: State<Resource<MainPageResponse>> = _mainPageState

    init {
        getMainPage()
    }

    private fun getMainPage() {
        viewModelScope.launch {
            _mainPageState.value = Resource.Loading()
            _mainPageState.value = mainPageRepository.getMainPage()
        }
    }
}
