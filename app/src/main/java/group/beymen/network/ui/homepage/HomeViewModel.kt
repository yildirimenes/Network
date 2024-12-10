package group.beymen.network.ui.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.model.homepage.HomeResponseModel
import group.beymen.network.data.repository.HomePageRepository
import group.beymen.network.data.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homePageRepository: HomePageRepository
) : ViewModel() {

    private val _mainPageState = MutableStateFlow<Resource<HomeResponseModel>>(Resource.Loading())
    val mainPageState: StateFlow<Resource<HomeResponseModel>> = _mainPageState

    init {
        getLoadPage()
    }

    fun getLoadPage() {
        viewModelScope.launch {
            _mainPageState.value = Resource.Loading()
            val result = homePageRepository.getMainPage()
            _mainPageState.value = result
        }
    }
}