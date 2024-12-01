package group.beymen.network.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import group.beymen.network.data.model.favorite.FavoriteProductEntity
import group.beymen.network.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteProductEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteProductEntity>> = _favorites

    init {
        viewModelScope.launch {
            repository.getFavorites().collect {
                _favorites.value = it
            }
        }
    }

    fun removeFavorite(product: FavoriteProductEntity) {
        viewModelScope.launch {
            repository.removeFavorite(product)
        }
    }

}
