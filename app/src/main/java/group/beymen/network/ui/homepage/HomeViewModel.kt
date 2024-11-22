package group.beymen.network.ui.homepage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.model.homepage.HomePageResponse
import group.beymen.network.data.repository.HomePageRepository
import group.beymen.network.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homePageRepository: HomePageRepository
) : ViewModel() {

    private val _mainPageState = mutableStateOf<Resource<HomePageResponse>>(Resource.Loading())
    val mainPageState: State<Resource<HomePageResponse>> = _mainPageState

    init {
        getMainPage()
    }

    private fun getMainPage() {
        viewModelScope.launch {
            _mainPageState.value = Resource.Loading()
            _mainPageState.value = homePageRepository.getMainPage()
        }
    }
}
