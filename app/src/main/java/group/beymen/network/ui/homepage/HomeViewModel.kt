package group.beymen.network.ui.homepage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.repository.HomePageRepository
import group.beymen.network.data.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homePageRepository: HomePageRepository
) : ViewModel() {

    private val _mainPageState = mutableStateOf<Resource<HomeResponseModel>>(Resource.Loading())
    val mainPageState: State<Resource<HomeResponseModel>> = _mainPageState

    init {
        getMainPage()
    }

    fun getMainPage() {
        viewModelScope.launch {
            _mainPageState.value = Resource.Loading()
            _mainPageState.value = homePageRepository.getMainPage()
        }
    }
}
