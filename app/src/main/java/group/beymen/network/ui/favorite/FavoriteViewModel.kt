package group.beymen.network.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/*
@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: FavoriteRepository) : ViewModel() {

    val favoriteProducts = repository.favoriteProducts

    fun addFavorite(product: FavoriteProduct) {
        viewModelScope.launch {
            repository.addToFavorites(product)
        }
    }

    fun removeFavorite(productId: Int) {
        viewModelScope.launch {
            repository.removeFromFavorites(productId)
        }
    }
}*/
